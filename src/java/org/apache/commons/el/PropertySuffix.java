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
 * <p>Represents an operator that obtains the value of another value's
 * property.  This is a specialization of ArraySuffix - a.b is
 * equivalent to a["b"]
 * 
 * @author Nathan Abramson - Art Technology Group
 * @author Shawn Bayern
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author$
 **/

public class PropertySuffix
  extends ArraySuffix
{
  //-------------------------------------
  // Properties
  //-------------------------------------
  // property name

  String mName;
  public String getName ()
  { return mName; }
  public void setName (String pName)
  { mName = pName; }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public PropertySuffix (String pName)
  {
    super (null);
    mName = pName;
  }

  //-------------------------------------
  /**
   *
   * Gets the value of the index
   **/
  Object evaluateIndex (VariableResolver pResolver, FunctionMapper functions)
    throws ELException
  {
    return mName;
  }

  //-------------------------------------
  /**
   *
   * Returns the operator symbol
   **/
  String getOperatorSymbol ()
  {
    return ".";
  }

  //-------------------------------------
  // ValueSuffix methods
  //-------------------------------------
  /**
   *
   * Returns the expression in the expression language syntax
   **/
  public String getExpressionString ()
  {
    return "." + StringLiteral.toIdentifierToken (mName);
  }

  //-------------------------------------
}
