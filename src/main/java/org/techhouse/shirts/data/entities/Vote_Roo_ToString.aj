// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.techhouse.shirts.data.entities;

import java.lang.String;

privileged aspect Vote_Roo_ToString {
    
    public String Vote.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Member: ").append(getMember()).append(", ");
        sb.append("Design: ").append(getDesign());
        return sb.toString();
    }
    
}
