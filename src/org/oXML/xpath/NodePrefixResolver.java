package org.oXML.xpath;

import java.util.Collection;
import java.util.Iterator;
import org.oXML.type.Name;
import org.oXML.xpath.parser.QName;
import org.oXML.util.Log;
import org.oXML.type.Node;
import org.oXML.type.AttributeNode;
import org.oXML.type.ElementNode;

/**
 */
public class NodePrefixResolver extends PrefixResolver {

    private Node ctxt;

    public NodePrefixResolver(Node ctxt){
        this.ctxt = ctxt;
    }

    public String getNamespaceURI(String prefix){
        if(prefix.equals(XMLNS_PREFIX))
            return XMLNS_NAMESPACE;
	return resolve(prefix, ctxt);
    }

    public static String resolve(String prefix, Node ctxt) {
        if(ctxt == null)
            return null;
        if(ctxt.getType().instanceOf(ElementNode.TYPE)){
            ElementNode e = (ElementNode)ctxt.cast(ElementNode.TYPE);
            Collection attributes = e.getAttributes();
            String ns = resolve(prefix, attributes);
            if(ns != null)
                return ns;
        }
        // look upwards
        return resolve(prefix, ctxt.getParent());
    }

    /**
     * look through the attributes for namespace declarations
     */
    private static String resolve(String prefix, Collection attributes) {
        Iterator it = attributes.iterator();
        while(it.hasNext()){
            AttributeNode attr = (AttributeNode)it.next();
            String aname = attr.getName().getLocalName();
//              if(aname.equals("xmlns") && prefix.equals("")){
//                  return attr.getValue();
//              }else{
            int index = aname.indexOf(':');
            if(index > 0 && aname.substring(0, index).equals(XMLNS_PREFIX)){
                String pre = aname.substring(index + 1);
                if(prefix.equals(pre))
                    return attr.getValue();
            }
//              }
        }
        return null;
    }
}
/*
    ObjectBox - o:XML compiler and interpretor
    for more information see http://www.o-xml.org/objectbox
    Copyright (C) 2002/2003 Martin Klang, Alpha Plus Technology Ltd
    email: martin at hack.org

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
