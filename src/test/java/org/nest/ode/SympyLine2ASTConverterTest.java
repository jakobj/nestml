package org.nest.ode;

import static de.se_rwth.commons.logging.Log.error;

import de.se_rwth.commons.logging.Log;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nest.spl._ast.ASTDeclaration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SympyLine2ASTConverterTest {

  @BeforeClass
  public static void disableFailQuick() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testConvertLine2AST() {
    final SympyLine2ASTConverter converter = new SympyLine2ASTConverter();
    final String testExpr = "P00 real = -Tau*tau_in*(Tau*h*exp(h/Tau) + Tau*tau_in*exp(h/Tau) - Tau*tau_in*exp"
        + "(h/tau_in) - "
        + "h*tau_in*exp(h/Tau))*exp(-h/tau_in - h/Tau)/(C*(Tau**2 - 2*Tau*tau_in + tau_in**2)) # PXX";
    final ASTDeclaration testant = converter.convert(testExpr);
    assertNotNull(testant);
    assertEquals(1, testant.getVars().size());
    assertEquals("P00", testant.getVars().get(0));
  }

}
