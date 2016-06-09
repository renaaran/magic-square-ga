/*
 * Copyright (C) 2016 Renato Barros Arantes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cefet.rba.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class Config implements ReadProperty {
    private static final Config CONFIG = new Config();
    private Map<String, String> properties;
    
    private Config() {
        properties = new HashMap<>();
        try {
            loadFile();
        } catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
    private void loadFile() throws Exception {
        File file = new File("resources/properties.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("property");
        for (int i = 0, sz = nList.getLength(); i < sz; i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getAttribute("name");
                String value = element.getElementsByTagName("value").item(0).getTextContent();
                if (name != null) {
                    properties.put(name, value);
                }
            }
        }
    }
    
    @Override
    public String getProperty(final String name) {
        return properties.get(name);
    }
    
    @Override
    public double getPropertyAsDouble(final String name) {
        return Double.parseDouble(getProperty(name));
    }
    
    @Override
    public int getPropertyAsInt(final String name) {
        return Integer.parseInt(getProperty(name));
    }

	@Override
	public boolean getPropertyAsBoolean(String name) {
		String property = getProperty(name);
		if (property == null) {
			return false;
		}
		return Boolean.parseBoolean(property);
	}
	
    public static Config getInstance() {
        return CONFIG;
    }
}
