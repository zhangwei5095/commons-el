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
 * <p>The abstract class from which all expression types
 * derive.
 * 
 * @author Nathan Abramson - Art Technology Group
 * @author Shawn Bayern
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author$
 **/

public abstract class Expression
{
  //-------------------------------------
  // Member variables
  //-------------------------------------

  //-------------------------------------
  /**
   *
   * Returns the expression in the expression language syntax
   **/
  public abstract String getExpressionString ();

  //-------------------------------------
  /**
   *
   * Evaluates the expression in the given context
   **/
  public abstract Object evaluate (VariableResolver pResolver, FunctionMapper functions)
    throws ELException;

  //-------------------------------------

  /**
   * Returns an expression with all <code>FunctionInvocation</code>s replaced by
   * <code>BoundFunctionInvocation</code>s.
   * @param functions the functions to use in this transformation
   * @return an Expression identical to this expression except with all
   *    <code>FunctionInvocation</code>s replaced by
   *    <code>BoundFunctionInvocation</code>s.
   * @throws ELException if any of the functions in this <code>Expression</code> are
   *    not present in <code>functions</code>
   */
  public abstract Expression bindFunctions(FunctionMapper functions)
      throws ELException;

}
