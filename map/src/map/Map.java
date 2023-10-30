/*
AI Assignment done by:
    Kalkidan Tadesse
 */
package map;

import java.util.Vector;


public class Map {

    
    Location starting,destination,CMC,Bole,Gerji,Hayahulet,Megenagna,Kazanchis,AratKilo,temp;
    Route CB,BC,BH,BG,HB,HM,HK,KH,KA,KM,AM,AK,MA,MK,MG,MH,GM,GB; 
    
/*This class (Location) represents the named locations of the map.*/
private class Location {
    
    String nameOfLocation;
    Route routes; /*These holds the direct route from a one location to another */
    Location from; /* An attribute to trace from which of the possible neighboring location is reached*/
    int pathCost; /*Initially zero, holds the sum of all the step costs before reaching a location*/
    boolean visited; /*Instead of using a closed list this variable will be 'True' 
                      if the location has been expanded*/
    
        Location(String name){
             nameOfLocation=name;
             visited=false;
             pathCost=0;
                        }
}

/*This class represents a direct route from one location to another*/
private class Route {
    Location to; /*Location to which the route takes*/
    int cost; /*The cost of choosing this route*/
    Route nextRoute;/*One route will point to the next direct route out of/into a certain location */
}

void initialize(){
    
    CMC=new Location("CMC"); Bole=new Location("Bole");
    Gerji=new Location("Gerji"); Hayahulet=new Location("Hayahulet");
    Megenagna=new Location("Megenagna"); Kazanchis=new Location("Kazanchis");
    AratKilo=new Location("AratKilo");
    
     CB=new Route();HB= new Route();KA= new Route();MA= new Route();GM= new Route();
     BC=new Route();HM= new Route();KM= new Route();MK= new Route();GB= new Route();
     BH=new Route();HK= new Route();AM= new Route(); MG= new Route();
     BG=new Route();KH= new Route();AK= new Route();MH= new Route();
       
    
     /*Routes are named by using the initials of the locations they connect.
    A Single route on the map is represented by two route objects because 
    in the definition of route only destination location is referred so AB!=BA
   */
     makeRoute(CB,Bole,360);        makeRoute(MA,AratKilo,260); 
     makeRoute(BC,CMC,360);         makeRoute(MK,Kazanchis,260);  
     makeRoute(BH,Hayahulet,260);   makeRoute(MG,Gerji,125);
     makeRoute(BG,Gerji,260);       makeRoute(MH,Hayahulet,125);                                                     
     makeRoute(HK,Kazanchis,125);   makeRoute(AM,Megenagna,260); 
     makeRoute(HB,Bole,260);        makeRoute(AK,Kazanchis,125);
     makeRoute(HM,Megenagna,125);   makeRoute(GM,Megenagna,125);
     makeRoute(KM,Megenagna,260);   makeRoute(GB,Bole,260);
     makeRoute(KH,Hayahulet,125);
     makeRoute(KA,AratKilo,125);
     
    /*The routes from one location are linked in a cascading manner, for example
      MA -> MK -> MG -> MH -> null, therefore by assigning only MA as a route of
     Megenagna all other routes can be addressed*/                      
       BC.nextRoute=BH; MA.nextRoute=MK; 
       BH.nextRoute=BG; MK.nextRoute=MG;
       HB.nextRoute=HM; MG.nextRoute=MH;
       HM.nextRoute=HK; AM.nextRoute=AK;
       KH.nextRoute=KA; GM.nextRoute=GB;
       KA.nextRoute=KM; 
       
        CMC.routes=CB;
        Bole.routes=BC;
        Hayahulet.routes=HB;
        Kazanchis.routes=KH;
        Megenagna.routes=MA;
        AratKilo.routes=AM;
        Gerji.routes=GM;
            
}

void makeRoute(Route r,Location place,int costOfroute){
            r.to=place;
            r.cost=costOfroute;
     }

int pathCost(Location l){
     return l.pathCost;
      }

Location leastCostPath(Location l,Vector q){
   for(int i=0; i<=q.indexOf(q.lastElement());i++){
          Location j=(Location) q.get(i);
          if(j.pathCost <= l.pathCost)
              l=j;
}
   return l;
}

public String nameOf(Location l){
     return l.nameOfLocation;
}

public Location sourceLocation(Location l){
     return l.from;
}

public void resetLocation(Location l){
   l.from=null;
   l.pathCost=0;
   l.visited=false;
  }

        
public void uniformCostSearch(){
    //System.out.print(destination.nameOfLocation + " " + starting.nameOfLocation);
    if (starting == null) {
        System.out.println("No Point of Departure");
         return;
    }
    
    Vector queue = new Vector();
    queue.addElement(starting);
    
    while(!queue.isEmpty()){
        
     Location v=(Location) queue.firstElement();  
     
         v = leastCostPath(v,queue);
         
     queue.remove(v);
     v.visited=true;
     
     if (v==destination){System.out.print(destination.nameOfLocation + "<--");
            for(Location pt=destination.from; pt!=null; pt=pt.from)
                System.out.print(pt.nameOfLocation + "<--");
            System.out.println(destination.pathCost);
                             return;}
     
     Route E=v.routes;
     
     while(E!=null){
         
         if(!E.to.visited){
               if(!queue.contains(E.to)){
                 E.to.from=v;
                 E.to.pathCost=v.pathCost+E.cost;
                 queue.add(E.to);
                E=E.nextRoute;
               }
               else{
                   int x=queue.indexOf(E.to);
                   int costCheck= v.pathCost+E.cost;
                   Location g=(Location) queue.get(x);
                 if(g.pathCost>costCheck){
                     E.to.from=v;
                     E.to.pathCost=v.pathCost+E.cost;
                     queue.remove(x);
                     queue.add(E.to);
                     E=E.nextRoute;
                 }
                 else E=E.nextRoute;
               
               }
          
          }
          else E=E.nextRoute;         
     }   
    }
 }     


    public static void main(String[] args) {
        // TODO code application logic here
      Map m= new Map();
         m.initialize();
        
      MapInterface mp= new MapInterface();
      mp.setMap(m);
      mp.show();
      mp.setDefaultCloseOperation(MapInterface.EXIT_ON_CLOSE);
    }
    
}
