public class VnavCalculator {
    /*
  
  
    Unedited code from main repository
  
  
    */
    
    
    private void calculateAltitudeRestrictions() throws Exception {
        // Create lists to group the altitude restrictions (about = at, other = the 5th type that I cant find the name of)
        ArrayList<String> restrictions = new ArrayList<String>();
        ArrayList<String> above = new ArrayList<String>();
        ArrayList<String> below = new ArrayList<String>();
        ArrayList<String> about = new ArrayList<String>();
        ArrayList<String> block = new ArrayList<String>();
        ArrayList<String> other = new ArrayList<String>();

        List<LegSegment> legSegments = route.getLegSegments();

        for(LegSegment legSegment : legSegments){

            Leg leg = legSegment.getSegmentLeg();

            AltRestr altRestr = leg.getAltRestr();
            //System.out.println(altRestr);

            if(altRestr.getAltitude() != 0){
                double alongRouteDistance = RouteCalculationUtils.getAlongRouteDistanceToLegSegment(route,legSegment);
                RouteConstraint routeConstraint = new RouteConstraint(altRestr.getAltitude(),alongRouteDistance,altRestr,legSegment);
                routeConstraintsClimb.add(routeConstraint);

                // Group the altitude restrictions
                restrictions.add(altRestr.toString());
                if(altRestr.isAbove()) {
                    above.add(altRestr.toString());
                } else if(altRestr.isBelow()) {
                    below.add(altRestr.toString());
                } else if(altRestr.isAt()) {
                    about.add(altRestr.toString());
                } else if(altRestr.isBlock()) {
                    block.add(altRestr.toString());
                } else {
                    other.add(altRestr.toString());
                }
                System.out.println(altRestr + " | " + altRestr.isAbove() + " | " + altRestr.isBelow() + " | " + altRestr.isAt() + " | " + altRestr.isBlock());
            }
        }
        // I can't figure out how the watch variables work in Intellij lol
        System.out.println(restrictions);
        System.out.println(above);
        System.out.println(below);
        System.out.println(about);
        System.out.println(block);
        System.out.println(other);
    }
    
    
    /*
    
    
    Unedited Code from main repository
    
    
    */
    
    
}


/*
OUTPUT FROM THE BLOCK OF SYSTEM.OUT.PRINTLN()
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
RESTRICTIONS> []
ABOVE> []
BELOW> []
AT> []
BLOCK> []
OTHER> []
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
TABLE OF ALTRES TYPE
ALTRES| ABOVE| BELOW | AT    | BLOCK
4000A | true | false | false | false

RESTRICTIONS> [4000A]
ABOVE> [4000A]
BELOW> []
AT> []
BLOCK> []
OTHER> []
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
TABLE OF ALTRES TYPE
ALTRES| ABOVE| BELOW | AT    | BLOCK
4000A | true | false | false | false
4000 | false | false | true | false
412 | false | false | true | false
5000A | true | false | false | false
5000 | false | false | true | false

RESTRICTIONS> [4000A, 4000, 412, 5000A, 5000]
ABOVE> [4000A, 5000A]
BELOW> []
AT> [4000, 412, 5000]
BLOCK> []
OTHER> []
*/
