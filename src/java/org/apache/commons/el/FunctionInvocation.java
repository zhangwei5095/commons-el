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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * <p>Represents a function call.</p>
 * 
 * @author Shawn Bayern (in the style of Nathan's other classes)
 **/

public class FunctionInvocation
  extends Expression
{
    //-------------------------------------
    // Constants
    //-------------------------------------
    private static Log log = LogFactory.getLog(FunctionInvocation.class);
    
  //-------------------------------------
  // Properties
  //-------------------------------------
  // property index

  private String functionName;
  private List argumentList;
  public String getFunctionName() { return functionName; }
  public void setFunctionName(String f) { functionName = f; }
  public List getArgumentList() { return argumentList; }
  public void setArgumentList(List l) { argumentList = l; }

  //-------------------------------------
  /**
   * Constructor
   **/
  public FunctionInvocation (String functionName, List argumentList)
  {
    this.functionName = functionName;
    this.argumentList = argumentList;
  }

  //-------------------------------------
  // Expression methods
  //-------------------------------------
  /**
   * Returns the expression in the expression language syntax
   **/
  public String getExpressionString ()
  {
    StringBuffer b = new StringBuffer();
    b.append(functionName);
    b.append("(");
    Iterator i = argumentList.iterator();
    while (i.hasNext()) {
      b.append(((Expression) i.next()).getExpressionString());
      if (i.hasNext())
        b.append(", ");
    }
    b.append(")");
    return b.toString();
  }


  //-------------------------------------
  /**
   *
   * Evaluates by looking up the name in the VariableResolver
   **/
  public Object evaluate (VariableResolver pResolver,
			  FunctionMapper functions)
    throws ELException
  {

    Method target = resolveFunction(functions);
    if (target == null) {
        if (log.isErrorEnabled()) {
            String message = MessageUtil.getMessageWithArgs(
                Constants.UNKNOWN_FUNCTION, functionName);
            log.error(message);
            throw new ELException(message);
        }
    }      

    // ensure that the number of arguments matches the number of parameters
    Class[] params = target.getParameterTypes();
    if (params.length != argumentList.size()) {
        if (log.isErrorEnabled()) {
            String message = MessageUtil.getMessageWithArgs(
                Constants.INAPPROPRIATE_FUNCTION_ARG_COUNT,
                functionName, new Integer(params.length),
                new Integer(argumentList.size()));
            log.error(message);
            throw new ELException(message);
        }      
    }

    // now, walk through each parameter, evaluating and casting its argument
    Object[] arguments = new Object[argumentList.size()];
    for (int i = 0; i < params.length; i++) {
      // evaluate
      arguments[i] = ((Expression) argumentList.get(i)).evaluate(pResolver,
								 functions);
      // coerce
      arguments[i] = Coercions.coerce(arguments[i], params[i]);
    }

    // finally, invoke the target method, which we know to be static
    try {
      return (target.invoke(null, arguments));
    } catch (InvocationTargetException ex) {
        if (log.isErrorEnabled()) {
            String message = MessageUtil.getMessageWithArgs(
                Constants.FUNCTION_INVOCATION_ERROR,
                functionName);
            Throwable t = ex.getTargetException();
            log.error(message, t);
            throw new ELException(message, t);
        }      
      return null;
    } catch (Throwable t) {
        if (log.isErrorEnabled()) {
            String message = MessageUtil.getMessageWithArgs(
                Constants.FUNCTION_INVOCATION_ERROR,
                functionName);            
            log.error(message, t);
            throw new ELException(message, t); 
        }      
      return null;
    }
  }

  /**
   * Returns the <code>Method</code> which is mapped to the function
   * name used by this <code>FunctionInvocation</code>.
   * @param functions The function mappings in use for this evaluation
   * @return the <code>Method</code> to execute 
   * @throws ELException
   */
  protected Method resolveFunction(FunctionMapper functions) throws ELException {
      // if the Map is null, then the function is invalid 
      if (functions == null) { 
          return null;
      }                    

      // normalize function name
      String prefix = null; 
      String localName = null; 
      int index = functionName.indexOf( ':' );
      if (index == -1) {
        prefix = "";
        localName = functionName;
      } else {
        prefix = functionName.substring( 0, index );
        localName = functionName.substring( index + 1 );
      }       
  
      // ensure that the function's name is mapped
      Method target = (Method) functions.resolveFunction(prefix, localName);
   
       return target; 
   }

   public Expression bindFunctions(final FunctionMapper functions)
           throws ELException {
       final List argList = new ArrayList(argumentList.size());
       for (Iterator argIter = argumentList.iterator(); argIter.hasNext();) {
           Expression arg = (Expression) argIter.next();
           argList.add(arg.bindFunctions(functions));
       }
       return new BoundFunctionInvocation(
               resolveFunction(functions),
               functionName,
               argList);
   }

  //-------------------------------------
}
