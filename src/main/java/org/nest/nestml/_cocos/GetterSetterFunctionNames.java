
/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package org.nest.nestml._cocos;

import de.monticore.symboltable.Scope;
import de.se_rwth.commons.logging.Log;
import org.nest.nestml._ast.ASTFunction;
import org.nest.nestml._symboltable.NestmlSymbols;
import org.nest.nestml._symboltable.symbols.MethodSymbol;
import org.nest.nestml._symboltable.symbols.NeuronSymbol;
import org.nest.nestml._symboltable.symbols.VariableSymbol;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;
import static de.se_rwth.commons.logging.Log.error;

/**
 * Prohibits definition of setter/getters for declared variables which are not aliases.
 *
 * @author (last commit) ippen, plotnikov
 */
public class GetterSetterFunctionNames implements NESTMLASTFunctionCoCo {

  public void check(final ASTFunction fun) {
    String funName = fun.getName();

    final Optional<? extends Scope> enclosingScope = fun.getEnclosingScope();
    checkState(enclosingScope.isPresent(),
        "There is no scope assigned to the AST node: " + fun.getName());

    Optional<MethodSymbol> methodSymbol = NestmlSymbols.resolveMethod(fun);

    if (methodSymbol.isPresent()) {
      if (methodSymbol.get().getDeclaringNeuron().getType() == NeuronSymbol.Type.COMPONENT
          && funName.equals("get_instance")
          && methodSymbol.get().getParameterTypes().size() == 0) {

        final String msg = NestmlErrorStrings.getErrorMsgGet_InstanceDefined(this);

        error(msg, fun.get_SourcePositionStart());
        return;
      }

      if (funName.startsWith("get_") || funName.startsWith("set_")) {
        String varName = funName.substring(4);

        final Optional<VariableSymbol> var = enclosingScope.get().resolve(varName, VariableSymbol.KIND);

        if (var.isPresent()) {
          if (funName.startsWith("set_")) {
            final String msg = NestmlErrorStrings.getErrorMsgGeneratedFunctionDefined(this,funName,varName);
            error(msg, fun.get_SourcePositionStart());
          }

          if (funName.startsWith("get_")) {
            final String msg = NestmlErrorStrings.getErrorMsgGeneratedFunctionDefined(this,funName,varName);
            error(msg, fun.get_SourcePositionStart());
          }

        }
        else {
          Log.trace("Cannot resolve the variable: " + varName, getClass().getSimpleName());
        }

      }

    }
    else {
      Log.trace("The function is" + funName + " undefined.", getClass().getSimpleName());
    }

  }

}
