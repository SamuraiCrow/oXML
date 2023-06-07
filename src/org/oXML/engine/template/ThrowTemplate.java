package org.oXML.engine.template;

import java.util.List;
import java.util.ArrayList;
import org.oXML.ObjectBoxException;
import org.oXML.xpath.Expression;
import org.oXML.xpath.SourceLocator;
import org.oXML.engine.ProgramException;
import org.oXML.engine.RuntimeContext;
import org.oXML.engine.ObjectBoxProcessingException;
import org.oXML.type.Name;
import org.oXML.type.Node;
import org.oXML.type.Type;
import org.oXML.util.Log;

/**
 */
public class ThrowTemplate implements Template{

    private Expression exception;
    private SourceLocator location;

    public ThrowTemplate(Expression exception, SourceLocator location){
	this.exception = exception;
	this.location = location;
    }

    public void process(RuntimeContext ctxt)
        throws ObjectBoxException{

	Node node = exception.evaluate(ctxt);
	throw new ProgramException(node, location);
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