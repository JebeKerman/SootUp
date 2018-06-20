package de.upb.soot.jimple;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.upb.soot.Scene;
import de.upb.soot.core.SootClass;
import de.upb.soot.jimple.common.stmt.Stmt;

/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-1999 Raja Vallee-Rai
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */

/**
 * Partial implementation of trap (exception catcher), used within Body classes.
 */
@SuppressWarnings("serial")
public class AbstractTrap implements Trap, Serializable {
  /** The exception being caught. */
  protected transient SootClass exception;

  /** The first unit being trapped. */
  protected StmtBox beginStmtBox;

  /** The unit just before the last unit being trapped. */
  protected StmtBox endStmtBox;

  /** The unit to which execution flows after the caught exception is triggered. */
  protected StmtBox handlerStmtBox;

  /** The list of unitBoxes referred to in this Trap (begin, end and handler. */
  protected List<StmtBox> unitBoxes;

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    exception = Scene.v().getSootClass((String) in.readObject());
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    out.writeObject(exception.getName());
  }

  /** Creates an AbstractTrap with the given exception, handler, begin and end units. */
  protected AbstractTrap(SootClass exception, StmtBox beginStmtBox, StmtBox endStmtBox,
      StmtBox handlerStmtBox) {
    this.exception = exception;
    this.beginStmtBox = beginStmtBox;
    this.endStmtBox = endStmtBox;
    this.handlerStmtBox = handlerStmtBox;
    this.unitBoxes = Collections
        .unmodifiableList(Arrays.asList(beginStmtBox, endStmtBox, handlerStmtBox));
  }

  public Stmt getBeginStmt() {
    return beginStmtBox.getStmt();
  }

  public Stmt getEndStmt() {
    return endStmtBox.getStmt();
  }

  public Stmt getHandlerStmt() {
    return handlerStmtBox.getStmt();
  }

  public StmtBox getHandlerStmtBox() {
    return handlerStmtBox;
  }

  public StmtBox getBeginStmtBox() {
    return beginStmtBox;
  }

  public StmtBox getEndStmtBox() {
    return endStmtBox;
  }

  @Override
  public List<StmtBox> getStmtBoxes() {
    return unitBoxes;
  }

  @Override
  public void clearStmtBoxes() {
    for (StmtBox box : getStmtBoxes()) {
      box.setStmt(null);
    }
  }

  public SootClass getException() {
    return exception;
  }

  public void setBeginStmt(Stmt beginStmt) {
    beginStmtBox.setStmt(beginStmt);
  }

  public void setEndStmt(Stmt endStmt) {
    endStmtBox.setStmt(endStmt);
  }

  public void setHandlerStmt(Stmt handlerStmt) {
    handlerStmtBox.setStmt(handlerStmt);
  }

  public void setException(SootClass exception) {
    this.exception = exception;
  }

  @Override
  public Object clone() {
    throw new RuntimeException();
  }


}
