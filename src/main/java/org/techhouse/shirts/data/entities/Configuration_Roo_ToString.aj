// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.techhouse.shirts.data.entities;

import java.lang.String;

privileged aspect Configuration_Roo_ToString {
    
    public String Configuration.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Configuration: ").append(getConfiguration()).append(", ");
        sb.append("Deadline: ").append(getDeadline());
        return sb.toString();
    }
    
}
