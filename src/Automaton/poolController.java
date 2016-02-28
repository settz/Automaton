/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automaton;

/**
 *
 * @author tonym
 */
public class poolController {
    public static final int ON = 1;
    public static final int OFF = 0;
    public static final String CTRL_TOPIC = "Pool/Control",
                               STATUS_TOPIC = "Pool/RunState";
    
    public int nAutoMode,
               nFilter,
               nCleaning,
               nSolar,
               nIrrAutoMode,
               nIrrZone_1,
               nIrrZone_2,
               nIrrZone_3;
    public double nTemperature,
                  nPh;
}
