/*
 * Copyright 1999-2004 The Apache Software Foundation
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
package org.apache.commons.el;

import javax.servlet.jsp.el.ELException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * <p>This is the superclass for all relational operators (except ==
 * or !=)
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: scolebourne $
 **/

public abstract class RelationalOperator
  extends BinaryOperator
{

  //-------------------------------------
  /**
   *
   * Applies the operator to the given value
   **/
  public Object apply (Object pLeft, Object pRight)
    throws ELException
  {
    return Coercions.applyRelationalOperator (pLeft, pRight, this);
  }

  //-------------------------------------
  /**
   *
   * Applies the operator to the given double values
   **/
  public abstract boolean apply (double pLeft,
				 double pRight
                                 );
  
  //-------------------------------------
  /**
   *
   * Applies the operator to the given long values
   **/
  public abstract boolean apply (long pLeft,
				 long pRight
                                 );
  
  //-------------------------------------
  /**
   *
   * Applies the operator to the given String values
   **/
  public abstract boolean apply (String pLeft,
				 String pRight
                                 );

  //-------------------------------------


    /**
     * Applies the operator to the given BigDecimal values, returning a BigDecimal
     **/
    public abstract boolean apply(BigDecimal pLeft, BigDecimal pRight);

    //-------------------------------------

    /**
     * Applies the operator to the given BigDecimal values, returning a BigDecimal
     **/
    public abstract boolean apply(BigInteger pLeft, BigInteger pRight);

    //-------------------------------------


    /**
     * Test return value of BigInteger/BigDecimal A.compareTo(B).
     * @param val - result of BigInteger/BigDecimal compareTo() call
     * @return - true if result is less than 0, otherwise false
     */
    protected boolean isLess(int val) {
        if (val < 0)
            return true;
        else
            return false;
    }

    /**
     * Test return value of BigInteger/BigDecimal A.compareTo(B).
     * @param val - result of BigInteger/BigDecimal compareTo() call
     * @return - true if result is equal to 0, otherwise false
     */
    protected boolean isEqual(int val) {
        if (val == 0)
            return true;
        else
            return false;
    }

    /**
     * Test return value of BigInteger/BigDecimal A.compareTo(B).
     * @param val - result of BigInteger/BigDecimal compareTo() call
     * @return - true if result is greater than 0, otherwise false
     */
    protected boolean isGreater(int val) {
        if (val > 0)
            return true;
        else
            return false;
    }
}
