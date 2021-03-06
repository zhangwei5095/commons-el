/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.el;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

/**
 *
 * <p>Represents an expression String consisting of a mixture of
 * Strings and Expressions.
 * 
 * @author Nathan Abramson - Art Technology Group
 * @author Shawn Bayern
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author$
 **/

public class ExpressionString extends Expression
{
  //-------------------------------------
  // Properties
  //-------------------------------------
  // property elements

  Object [] mElements;
  public Object [] getElements ()
  { return mElements; }
  public void setElements (Object [] pElements)
  { mElements = pElements; }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public ExpressionString (Object [] pElements)
  {
    mElements = pElements;
  }

  //-------------------------------------
  /**
   *
   * Evaluates the expression string by evaluating each element,
   * converting it to a String (using toString, or "" for null values)
   * and concatenating the results into a single String.
   **/
  public Object evaluate (VariableResolver pResolver,
			  FunctionMapper functions)
    throws ELException
  {
    StringBuffer buf = new StringBuffer ();
    for (int i = 0; i < mElements.length; i++) {
      Object elem = mElements [i];
      if (elem instanceof String) {
	buf.append ((String) elem);
      }
      else if (elem instanceof Expression) {
	Object val = 
	  ((Expression) elem).evaluate (pResolver, functions);
	if (val != null) {
	  buf.append (val.toString ());
	}
      }
    }
    return buf.toString ();
  }

  //-------------------------------------
  /**
   *
   * Returns the expression in the expression language syntax
   **/
  public String getExpressionString ()
  {
    StringBuffer buf = new StringBuffer ();
    for (int i = 0; i < mElements.length; i++) {
      Object elem = mElements [i];
      if (elem instanceof String) {
	buf.append ((String) elem);
      }
      else if (elem instanceof Expression) {
	buf.append ("${");
	buf.append (((Expression) elem).getExpressionString ());
	buf.append ("}");
      }
    }
    return buf.toString ();
  }

  //-------------------------------------

  public Expression bindFunctions(FunctionMapper functions) throws ELException {
      final Object[] boundElements = new Object[mElements.length];
      for (int i = 0; i < mElements.length; i++) {
          if (mElements[i] instanceof Expression) {
              boundElements[i] = ((Expression)mElements[i]).bindFunctions(functions);
          } else {
              boundElements[i] = mElements[i];
          }
      }
      return new ExpressionString(boundElements);
  }
}
