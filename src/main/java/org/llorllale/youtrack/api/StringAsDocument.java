/*
 * Copyright 2017 George Aristy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.llorllale.youtrack.api;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Handy class to encapsulate an XML string as a {@link Document} node.
 * 
 * <p>It hides tedious code like:
 * <pre>
 * {@code
 * final Document doc = DocumentBuilderFactory.newInstance()
 *     .newDocumentBuilder()
 *     .parse(new InputSource(new StringReader(xml)));
 * }
 * </pre>
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @since 1.0.0
 */
@SuppressWarnings("checkstyle:MethodCount")
final class StringAsDocument implements Document {
  private final Document base;

  /**
   * Ctor.
   * 
   * <p>Throws {@link UncheckedException} because {@code xml} is expected to be well-formed and
   * the JDK's DOM infrastructure should be well-configured.
   * 
   * @param xml the xml string
   * @throws UncheckedException wrapping any {@link ParserConfigurationException}, 
   *   {@link SAXException}, {@link IOException} thrown by Java
   * @since 1.0.0
   */
  StringAsDocument(String xml) throws UncheckedException {
    try {
      this.base = DocumentBuilderFactory.newInstance()
          .newDocumentBuilder()
          .parse(new InputSource(new StringReader(xml)));
    } catch(ParserConfigurationException | SAXException | IOException e) {
      throw new UncheckedException(e.getMessage(), e);
    }
  }

  @Override
  public DocumentType getDoctype() {
    return this.base.getDoctype();
  }

  @Override
  public DOMImplementation getImplementation() {
    return this.base.getImplementation();
  }

  @Override
  public Element getDocumentElement() {
    return this.base.getDocumentElement();
  }

  @Override
  public Element createElement(String tagName) throws DOMException {
    return this.base.createElement(tagName);
  }

  @Override
  public DocumentFragment createDocumentFragment() {
    return this.base.createDocumentFragment();
  }

  @Override
  public Text createTextNode(String data) {
    return this.base.createTextNode(data);
  }

  @Override
  public Comment createComment(String data) {
    return this.base.createComment(data);
  }

  @Override
  public CDATASection createCDATASection(String data) throws DOMException {
    return this.base.createCDATASection(data);
  }

  @Override
  public ProcessingInstruction createProcessingInstruction(
      String target, 
      String data
  ) throws DOMException {
    return this.base.createProcessingInstruction(target, data);
  }

  @Override
  public Attr createAttribute(String name) throws DOMException {
    return this.base.createAttribute(name);
  }

  @Override
  public EntityReference createEntityReference(String name) throws DOMException {
    return this.base.createEntityReference(name);
  }

  @Override
  public NodeList getElementsByTagName(String tagname) {
    return this.base.getElementsByTagName(tagname);
  }

  @Override
  public Node importNode(Node importedNode, boolean deep) throws DOMException {
    return this.base.importNode(importedNode, deep);
  }

  @Override
  public Element createElementNS(String namespaceUri, String qualifiedName) throws DOMException {
    return this.base.createElementNS(namespaceUri, qualifiedName);
  }

  @Override
  public Attr createAttributeNS(String namespaceUri, String qualifiedName) throws DOMException {
    return this.base.createAttributeNS(namespaceUri, qualifiedName);
  }

  @Override
  public NodeList getElementsByTagNameNS(String namespaceUri, String localName) {
    return this.base.getElementsByTagNameNS(namespaceUri, localName);
  }

  @Override
  public Element getElementById(String elementId) {
    return this.base.getElementById(elementId);
  }

  @Override
  public String getInputEncoding() {
    return this.base.getInputEncoding();
  }

  @Override
  public String getXmlEncoding() {
    return this.base.getXmlEncoding();
  }

  @Override
  public boolean getXmlStandalone() {
    return this.base.getXmlStandalone();
  }

  @Override
  public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
    this.base.setXmlStandalone(xmlStandalone);
  }

  @Override
  public String getXmlVersion() {
    return this.base.getXmlVersion();
  }

  @Override
  public void setXmlVersion(String xmlVersion) throws DOMException {
    this.base.setXmlVersion(xmlVersion);
  }

  @Override
  public boolean getStrictErrorChecking() {
    return this.base.getStrictErrorChecking();
  }

  @Override
  public void setStrictErrorChecking(boolean strictErrorChecking) {
    this.base.setStrictErrorChecking(strictErrorChecking);
  }

  @Override
  public String getDocumentURI() {
    return this.base.getDocumentURI();
  }

  @Override
  public void setDocumentURI(String documentUri) {
    this.base.setDocumentURI(documentUri);
  }

  @Override
  public Node adoptNode(Node source) throws DOMException {
    return this.base.adoptNode(source);
  }

  @Override
  public DOMConfiguration getDomConfig() {
    return this.base.getDomConfig();
  }

  @Override
  public void normalizeDocument() {
    this.base.normalizeDocument();
  }

  @Override
  public Node renameNode(Node node, String namespaceUri, String qualifiedName) throws DOMException {
    return this.base.renameNode(node, namespaceUri, qualifiedName);
  }

  @Override
  public String getNodeName() {
    return this.base.getNodeName();
  }

  @Override
  public String getNodeValue() throws DOMException {
    return this.base.getNodeValue();
  }

  @Override
  public void setNodeValue(String nodeValue) throws DOMException {
    this.base.setNodeValue(nodeValue);
  }

  @Override
  public short getNodeType() {
    return this.base.getNodeType();
  }

  @Override
  public Node getParentNode() {
    return this.base.getParentNode();
  }

  @Override
  public NodeList getChildNodes() {
    return this.base.getChildNodes();
  }

  @Override
  public Node getFirstChild() {
    return this.base.getFirstChild();
  }

  @Override
  public Node getLastChild() {
    return this.base.getLastChild();
  }

  @Override
  public Node getPreviousSibling() {
    return this.base.getPreviousSibling();
  }

  @Override
  public Node getNextSibling() {
    return this.base.getNextSibling();
  }

  @Override
  public NamedNodeMap getAttributes() {
    return this.base.getAttributes();
  }

  @Override
  public Document getOwnerDocument() {
    return this.base.getOwnerDocument();
  }

  @Override
  public Node insertBefore(Node newChild, Node refChild) throws DOMException {
    return this.base.insertBefore(newChild, refChild);
  }

  @Override
  public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
    return this.base.replaceChild(newChild, oldChild);
  }

  @Override
  public Node removeChild(Node oldChild) throws DOMException {
    return this.base.removeChild(oldChild);
  }

  @Override
  public Node appendChild(Node newChild) throws DOMException {
    return this.base.appendChild(newChild);
  }

  @Override
  public boolean hasChildNodes() {
    return this.base.hasChildNodes();
  }

  @Override
  public Node cloneNode(boolean deep) {
    return this.base.cloneNode(deep);
  }

  @Override
  public void normalize() {
    this.base.normalize();
  }

  @Override
  public boolean isSupported(String feature, String version) {
    return this.base.isSupported(feature, version);
  }

  @Override
  public String getNamespaceURI() {
    return this.base.getNamespaceURI();
  }

  @Override
  public String getPrefix() {
    return this.base.getPrefix();
  }

  @Override
  public void setPrefix(String prefix) throws DOMException {
    this.base.setPrefix(prefix);
  }

  @Override
  public String getLocalName() {
    return this.base.getLocalName();
  }

  @Override
  public boolean hasAttributes() {
    return this.base.hasAttributes();
  }

  @Override
  public String getBaseURI() {
    return this.base.getBaseURI();
  }

  @Override
  public short compareDocumentPosition(Node other) throws DOMException {
    return this.base.compareDocumentPosition(other);
  }

  @Override
  public String getTextContent() throws DOMException {
    return this.base.getTextContent();
  }

  @Override
  public void setTextContent(String textContent) throws DOMException {
    this.base.setTextContent(textContent);
  }

  @Override
  public boolean isSameNode(Node other) {
    return this.base.isSameNode(other);
  }

  @Override
  public String lookupPrefix(String namespaceUri) {
    return this.base.lookupPrefix(namespaceUri);
  }

  @Override
  public boolean isDefaultNamespace(String namespaceUri) {
    return this.base.isDefaultNamespace(namespaceUri);
  }

  @Override
  public String lookupNamespaceURI(String prefix) {
    return this.base.lookupNamespaceURI(prefix);
  }

  @Override
  public boolean isEqualNode(Node arg) {
    return this.base.isEqualNode(arg);
  }

  @Override
  public Object getFeature(String feature, String version) {
    return this.base.getFeature(feature, version);
  }

  @Override
  public Object setUserData(String key, Object data, UserDataHandler handler) {
    return this.base.setUserData(key, data, handler);
  }

  @Override
  public Object getUserData(String key) {
    return this.base.getUserData(key);
  }
}
