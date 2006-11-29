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

import java.math.BigDecimal;

import javax.servlet.jsp.el.ELException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * <p>The implementation of the divide operator
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author$
 **/

public class DivideOperator
  extends BinaryOperator
{
    //-------------------------------------
    // Constants
    //-------------------------------------
    private static Log log = LogFactory.getLog(DivideOperator.class);
    
  //-------------------------------------
  // Singleton
  //-------------------------------------

  public static final DivideOperator SINGLETON =
    new DivideOperator ();

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public DivideOperator ()
  {
  }

  //-------------------------------------
  // Expression methods
  //-------------------------------------
  /**
   *
   * Returns the symbol representing the operator
   **/
  public String getOperatorSymbol ()
  {
    return "/";
  }

  //-------------------------------------
  /**
   *
   * Applies the operator to the given value
   **/
  public Object apply (Object pLeft,
		       Object pRight)
    throws ELException
  {
    if (pLeft == null &&
	pRight == null) {
        if (log.isWarnEnabled()) {
            log.warn(
                MessageUtil.getMessageWithArgs(
                    Constants.ARITH_OP_NULL,
                    getOperatorSymbol()));
        }     
      return PrimitiveObjects.getInteger (0);
    }

    if (Coercions.isBigDecimal(pLeft) ||
        Coercions.isBigInteger(pLeft) ||
        Coercions.isBigDecimal(pRight) ||
        Coercions.isBigInteger(pRight)) {

        BigDecimal left = (BigDecimal)
            Coercions.coerceToPrimitiveNumber(pLeft, BigDecimal.class);
        BigDecimal right = (BigDecimal)
            Coercions.coerceToPrimitiveNumber(pRight, BigDecimal.class);

        try {
            return left.divide(right, BigDecimal.ROUND_HALF_UP);
        } catch (Exception exc) {
            if (log.isErrorEnabled()) {
                String message = MessageUtil.getMessageWithArgs(
                    Constants.ARITH_ERROR,
                    getOperatorSymbol(),
                    "" + left,
                    "" + right); 
                log.error(message);
                throw new ELException(message);
            }            
            return PrimitiveObjects.getInteger(0);
        }
    } else {

        double left =
            Coercions.coerceToPrimitiveNumber(pLeft, Double.class).
            doubleValue();
        double right =
            Coercions.coerceToPrimitiveNumber(pRight, Double.class).
            doubleValue();

        try {
            return PrimitiveObjects.getDouble(left / right);
        } catch (Exception exc) {
            if (log.isErrorEnabled()) {
                String message = MessageUtil.getMessageWithArgs(
                    Constants.ARITH_ERROR,
                    getOperatorSymbol(),
                    "" + left,
                    "" + right);
                log.error(message);
                throw new ELException(message);
            }         
            return PrimitiveObjects.getInteger(0);
        }
    }
  }

  //-------------------------------------
}
