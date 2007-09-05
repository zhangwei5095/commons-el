/* $Id$ */
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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.Expression;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import org.apache.commons.el.ExpressionEvaluatorImpl;

import junit.framework.TestCase;

/**
 * @author Jamie Taylor
 *
 */
public class FunctionBindingTest extends TestCase {
    public static String before() {return "before";}
    public static String after() {return "after";}
    public static String echo(final String s) {return s;}
    private static class UpdatableFunctionMapper implements FunctionMapper {
        private Map mappings = new HashMap();
        public void setMapping(
                final String name,
                final Method function) 
        {
            mappings.put(name,function);
        }
        public Method resolveFunction(
                final String prefix, 
                final String localName) 
        {
            return (Method)mappings.get(localName);
        }
    }
    
    public void testFunctionBinding() throws ELException, NoSuchMethodException{
        final String theExpression = "${theFunction()}";
        final String nestedExpression = "${echo(theFunction())}";
        final VariableResolver emptyVariableResolver = new VariableResolver() {
            public Object resolveVariable(String arg0) throws ELException {
                return null;
            }
        };
        final UpdatableFunctionMapper fm = new UpdatableFunctionMapper();
        final Method before = FunctionBindingTest.class.getDeclaredMethod("before",new Class[0]);
        final Method after = FunctionBindingTest.class.getDeclaredMethod("after",new Class[0]);
        final Method echo = FunctionBindingTest.class.getDeclaredMethod("echo", new Class[]{String.class});
        final ExpressionEvaluator evaluator = new ExpressionEvaluatorImpl();
        fm.setMapping("theFunction",before);
        fm.setMapping("echo", echo);
        
        final Expression expr = evaluator.parseExpression(theExpression, String.class, fm);
        final Expression nestedExpr = evaluator.parseExpression(nestedExpression, String.class, fm);
        assertEquals(
           "before",
           expr.evaluate(emptyVariableResolver));
        assertEquals(
           "before",
           nestedExpr.evaluate(emptyVariableResolver));
        
        fm.setMapping("theFunction",after);
        assertEquals(
           "after",
           evaluator.evaluate(theExpression, String.class, emptyVariableResolver, fm));
        assertEquals(
           "before",
           expr.evaluate(emptyVariableResolver));
        assertEquals(
           "after",
           evaluator.evaluate(nestedExpression, String.class, emptyVariableResolver, fm));
        assertEquals(
           "before",
           nestedExpr.evaluate(emptyVariableResolver));
    }
}
