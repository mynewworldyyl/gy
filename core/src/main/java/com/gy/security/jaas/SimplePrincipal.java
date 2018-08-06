package com.gy.security.jaas;

import java.io.Serializable;
import java.security.Principal;

public class SimplePrincipal implements Principal, Serializable {
  private static final long serialVersionUID = 1L;
  private String name;

  public SimplePrincipal(String name) {
    if (name == null) throw new NullPointerException("illegal null input");

    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public String toString() {
    return "JMXPrincipal:  " + this.name;
  }

  public boolean equals(Object o) {
    if (o == null) return false;

    if (this == o) return true;

    if (!(o instanceof SimplePrincipal)) return false;
    SimplePrincipal that = (SimplePrincipal)o;

    return getName().equals(that.getName());
  }

  public int hashCode(){
    return this.name.hashCode();
  }
}