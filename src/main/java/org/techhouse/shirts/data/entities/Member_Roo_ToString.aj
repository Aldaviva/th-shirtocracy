// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.techhouse.shirts.data.entities;

import java.lang.String;

privileged aspect Member_Roo_ToString {
    
    public String Member.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("Role: ").append(getRole());
        return sb.toString();
    }
    
}