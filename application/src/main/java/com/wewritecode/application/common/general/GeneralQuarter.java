/**
 * @author Grant Clark
 */

package com.wewritecode.application.common.general;

public class GeneralQuarter implements Comparable<GeneralQuarter> {
    private String quarter;
    private long lastUpdated;

    public String getQuarter() { return quarter; }
    public void setQuarter(String quarter) { this.quarter = quarter; }
    public long getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(long lastUpdated) { this.lastUpdated = lastUpdated; }

    @Override
    public int compareTo(GeneralQuarter o) {
        if (o == null)
            return 1;
        return (int) (this.lastUpdated - o.lastUpdated);
    }
}
