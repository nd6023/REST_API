package edu.sjsu.location.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import edu.sjsu.location.pojo.FinalScore;
import edu.sjsu.location.pojo.Game;
import edu.sjsu.location.pojo.Incident;
import edu.sjsu.location.pojo.Location;
import edu.sjsu.location.pojo.LocationList;
import edu.sjsu.location.pojo.Question;
import edu.sjsu.location.pojo.Score;
import edu.sjsu.location.pojo.Street;


@Path("/rest")
public class RestCRUDService {
    
    ArrayList<Location> locMap = new ArrayList<Location>();
    //HashMap<String, Location> locMap = new HashMap<String, Location>();
    
    @POST
    @Path("/postgame")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(Game game ) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017 collisionPrevention
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("gamedetails");
            
            
            
            // create a row (document) in collection based on the object sent from
            // post call
            BasicDBObject document = new BasicDBObject();
            document.put("gameName", game.getGameName());
            document.put("playerName", game.getPlayerName());
            
            
            table.insert(document);
            
            
            /*
            // searching the DB to get the document for the given client
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("id", incident.getLatitude());
            DBCursor cursor = table.find(searchQuery);
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String detailsObj = nextDocument.toString();
                result += detailsObj + "\n";
            }
            */
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // sending the registered client information to the client
        return Response.status(201).entity(result).build();
    }
    
    @GET
    @Path("/getgame/{gameName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readGame(@PathParam("gameName") String gameName) {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("gamedetails");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("gameName", gameName);
            DBCursor cursor = table.find(searchQuery);
            result = "{\"game\": [";
            //String strTime = "";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString(); 
                String subDetails = "{"; 
                subDetails +=	details.substring(51, details.length());
                
               	
                result += subDetails + "\n";
                result += ",";
                	
                 
                
            }
            //
            result=result.substring(0, result.length() - 1);
            result +="]}";            
            mongoClient.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // sending the updated records back to the client
        return Response.status(201).entity(result).build();
        
    }
    
 // Get all
    @GET
    @Path("/getgame")
    @Produces(MediaType.APPLICATION_JSON)
    public String readAllGame() {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("gamedetails");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            //searchQuery.put("registrationID", registrationID);
            DBCursor cursor = table.find();
            Location loc= null;
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                loc= new Location();
            }
            mongoClient.close();
            /*Location curLoc = new Location("99",99,99,99,"N",99,"99");
             System.out.println("Is it safe to make a move? " + isSafe(curLoc, locMap));*/
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="MongoDB server is inactive";
        }
        
        // sending the updated records back to the client
        return result;
        
    }
    
    @DELETE
    @Path("/deletegame/{gameName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deRegis(@PathParam("gameName") String gameName) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            DBCollection table = db.getCollection("gamedetails");
            BasicDBObject deleteQuery = new BasicDBObject().append("gameName", gameName);
            
            table.remove(deleteQuery);
            
            mongoClient.close();
            // sending the response of deRegister function to the client
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        return Response.status(201).entity(result).build();
    }

    
    @POST
    @Path("/postques")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerQuestion(Question question ) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017 collisionPrevention
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            DBCollection table = db.getCollection("questiondetails");
            
            
            
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("gameID", question.getGameID());
            DBCursor cursor = table.find(searchQuery);
            String strTime = "";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                
                // ++ ND
                strTime = (String) nextDocument.get("gameID");
                // -- ND
                
            }
            if(strTime.equals(""))
            {
                //System.out.println("The record is not yet inserted at \t\t" + strTime );
                BasicDBObject document = new BasicDBObject();
                document.put("gameID", question.getGameID());
                document.put("ques", question.getQues());
                
                table.insert(document);
                //System.out.println("Person will be going ====>"+clientID);
                
                // searching the DB to get the document for the given client
                BasicDBObject searchQueryP = new BasicDBObject();
                searchQueryP.put("gameID", question.getGameID());
                DBCursor cursorP = table.find(searchQueryP);
                result = "";
                while (cursorP.hasNext()) {
                    DBObject nextDocument = cursorP.next();
                    String detailsObj = nextDocument.toString();
                    result += detailsObj + "\n";
                }
                
                
            }
            else {
                //System.out.println("The record was inserted at \t\t" + strTime );
                BasicDBObject updatedClient = new BasicDBObject();
                updatedClient.put("gameID", question.getGameID());
                updatedClient.put("ques", question.getQues());
                BasicDBObject updateQuery = new BasicDBObject().append("gameID", question.getGameID());
                
                table.update(updateQuery, updatedClient);
                
                // searching the DB to retrieve the updated details of the client
                BasicDBObject searchQueryPut = new BasicDBObject();
                searchQueryPut.put("gameID", question.getGameID());
                DBCursor cursorPut = table.find(searchQueryPut);
                result = "";
                while (cursorPut.hasNext()) {
                    DBObject nextDocument = cursorPut.next();
                    String details = nextDocument.toString();
                    result += details + "\n";
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return Response.status(201).entity(result).build();
    }
    
    
    @GET
    @Path("/getques/{gameID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readQues(@PathParam("gameID") String gameID) {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("questiondetails");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("gameID", gameID);
            DBCursor cursor = table.find(searchQuery);
            //result = "{\"question\": [";
            //String strTime = "";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString(); 
                String subDetails = "{"; 
                subDetails +=	details.substring(51, details.length());
                
               	
                result += subDetails + "\n";
                //result += ",";
                	
                 
                
            }
            //
           // result=result.substring(0, result.length() - 1);
            //result +="]}";            
            mongoClient.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // sending the updated records back to the client
        return Response.status(201).entity(result).build();
        
    }
    
    
    
 // Get all
    @GET
    @Path("/getques")
    @Produces(MediaType.APPLICATION_JSON)
    public String readAllQues() {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("questiondetails");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            //searchQuery.put("registrationID", registrationID);
            DBCursor cursor = table.find();
            Location loc= null;
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                loc= new Location();
            }
            mongoClient.close();
            /*Location curLoc = new Location("99",99,99,99,"N",99,"99");
             System.out.println("Is it safe to make a move? " + isSafe(curLoc, locMap));*/
    
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="MongoDB server is inactive";
        }
        
        // sending the updated records back to the client
        return result;
        
    }
    
    @DELETE
    @Path("/deleteques/{gameID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deRegisQues(@PathParam("gameID") String gameID) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("questiondetails");
            BasicDBObject deleteQuery = new BasicDBObject().append("gameID", gameID);
            
            table.remove(deleteQuery);
            
            mongoClient.close();
            // sending the response of deRegister function to the client
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        return Response.status(201).entity(result).build();
    }
    
    

    
    @POST
    @Path("/postscore")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerScore(Score score ) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017 collisionPrevention
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            DBCollection table = db.getCollection("scoredetails");
            
            
            
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("quesId", score.getQuesId());
            DBCursor cursor = table.find(searchQuery);
            int intTime = 0;
            int intScore = 0;
            String quesId_n = null;
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                
                // ++ ND
                intTime = Integer.parseInt((String) nextDocument.get("time"));
                intScore = Integer.parseInt((String) nextDocument.get("score"));
                quesId_n = (String) nextDocument.get("quesId");
                // -- ND
                
            }
            if(intTime <= 0)
            {
                //System.out.println("The record is not yet inserted at \t\t" + strTime );
                BasicDBObject document = new BasicDBObject();
                document.put("gameID", score.getGameID());
                document.put("score", score.getScore());
                document.put("time", score.getTime());
                document.put("playerName", score.getPlayerName());
                document.put("quesId", score.getQuesId());
                
                table.insert(document);
                //System.out.println("Person will be going ====>"+clientID);
                
                // searching the DB to get the document for the given client
                BasicDBObject searchQueryP = new BasicDBObject();
                searchQueryP.put("quesId", score.getQuesId());
                DBCursor cursorP = table.find(searchQueryP);
                result = "";
                while (cursorP.hasNext()) {
                    DBObject nextDocument = cursorP.next();
                    String detailsObj = nextDocument.toString();
                    result += detailsObj + "\n";
                }
                
                
            }
            else  if(!score.getQuesId().equals(quesId_n)) {
            	
            	BasicDBObject updatedClient = new BasicDBObject();
                updatedClient.put("gameID", score.getGameID());
                updatedClient.put("score", score.getScore());
                updatedClient.put("time", score.getTime());
                updatedClient.put("playerName", score.getPlayerName());
                updatedClient.put("quesId", score.getQuesId());
                BasicDBObject updateQuery = new BasicDBObject().append("quesId", score.getQuesId());
                
                table.update(updateQuery, updatedClient);
                
                // searching the DB to retrieve the updated details of the client
                BasicDBObject searchQueryPut = new BasicDBObject();
                searchQueryPut.put("quesId", score.getQuesId());
                DBCursor cursorPut = table.find(searchQueryPut);
                result = "";
                while (cursorPut.hasNext()) {
                    DBObject nextDocument = cursorPut.next();
                    String details = nextDocument.toString();
                    result += details + "\n";
                }
            	
            }
            else if ((intScore < Integer.parseInt(score.getScore())) || (intScore == Integer.parseInt(score.getScore()) && intTime > Integer.parseInt(score.getTime()))){
                //System.out.println("The record was inserted at \t\t" + strTime );
                BasicDBObject updatedClient = new BasicDBObject();
                updatedClient.put("gameID", score.getGameID());
                updatedClient.put("score", score.getScore());
                updatedClient.put("time", score.getTime());
                updatedClient.put("playerName", score.getPlayerName());
                updatedClient.put("quesId", score.getQuesId());
                BasicDBObject updateQuery = new BasicDBObject().append("quesId", score.getQuesId());
                
                table.update(updateQuery, updatedClient);
                
                // searching the DB to retrieve the updated details of the client
                BasicDBObject searchQueryPut = new BasicDBObject();
                searchQueryPut.put("quesId", score.getQuesId());
                DBCursor cursorPut = table.find(searchQueryPut);
                result = "";
                while (cursorPut.hasNext()) {
                    DBObject nextDocument = cursorPut.next();
                    String details = nextDocument.toString();
                    result += details + "\n";
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return Response.status(201).entity(result).build();
    }
    
    
    @GET
    @Path("/getquestscore/{gameID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readQuestScore(@PathParam("gameID") String gameID) {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("scoredetails");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("gameID", gameID);
            DBCursor cursor = table.find(searchQuery);
            //result = "{\"question\": [";
            //String strTime = "";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString(); 
                String subDetails = "{"; 
                subDetails +=	details.substring(51, details.length());
                
               	
                result += subDetails + "\n";
                //result += ",";
                	
                 
                
            }
            //
           // result=result.substring(0, result.length() - 1);
            //result +="]}";            
            mongoClient.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // sending the updated records back to the client
        return Response.status(201).entity(result).build();
        
    }
    
    /*
    @GET
    @Path("/getfinalscore/{playerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readScore(@PathParam("playerName") String playerName) {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("scoredetails");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("playerName", playerName);
            DBCursor cursor = table.find(searchQuery);
            result = "{\"playerscore\": [";
            //String strTime = "";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString(); 
                String subDetails = "{"; 
                subDetails +=	details.substring(51, details.length());
                
               	
                result += subDetails + "\n";
                result += ",";
                	
                 
                
            }
            //
            result=result.substring(0, result.length() - 1);
            result +="]}";            
            mongoClient.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // sending the updated records back to the client
        return Response.status(201).entity(result).build();
        
    }
    */
    
 // Get all
    @GET
    @Path("/getscore")
    @Produces(MediaType.APPLICATION_JSON)
    public String readAllScore() {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("scoredetails");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            //searchQuery.put("registrationID", registrationID);
            DBCursor cursor = table.find();
            //Location loc= null;
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                //loc= new Location();
            }
            mongoClient.close();
            /*Location curLoc = new Location("99",99,99,99,"N",99,"99");
             System.out.println("Is it safe to make a move? " + isSafe(curLoc, locMap));*/
    
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="MongoDB server is inactive";
        }
        
        // sending the updated records back to the client
        return result;
        
    }
    
    @DELETE
    @Path("/deletescore/{quesId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deRegisScore(@PathParam("quesId") String quesId) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("scoredetails");
            BasicDBObject deleteQuery = new BasicDBObject().append("quesId", quesId);
            
            table.remove(deleteQuery);
            
            mongoClient.close();
            // sending the response of deRegister function to the client
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        return Response.status(201).entity(result).build();
    }
    
    
    @DELETE
    @Path("/deletegamescore/{gameID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deRegisGameScore(@PathParam("gameID") String gameID) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("scoredetails");
            BasicDBObject deleteQuery = new BasicDBObject().append("gameID", gameID);
            
            table.remove(deleteQuery);
            
            mongoClient.close();
            // sending the response of deRegister function to the client
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        return Response.status(201).entity(result).build();
    }
    
    // Final score
    
    
    @POST
    @Path("/postfinalscore")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerFinalScore(FinalScore score ) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017 collisionPrevention
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            DBCollection table = db.getCollection("finalscoredetails");
            
            
            /*
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("quesId", score.getQuesId());
            DBCursor cursor = table.find(searchQuery);
            int intTime = 0;
            int intScore = 0;
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                
                // ++ ND
                intTime = Integer.parseInt((String) nextDocument.get("time"));
                intScore = Integer.parseInt((String) nextDocument.get("score"));
                // -- ND
                
            }
            */
            {
                //System.out.println("The record is not yet inserted at \t\t" + strTime );
                BasicDBObject document = new BasicDBObject();
                document.put("gameID", score.getGameID());
                document.put("score", score.getScore());
                document.put("playerName", score.getPlayerName());
                
                table.insert(document);
                //System.out.println("Person will be going ====>"+clientID);
                
                // searching the DB to get the document for the given client
                BasicDBObject searchQueryP = new BasicDBObject();
                searchQueryP.put("playerName", score.getPlayerName());
                DBCursor cursorP = table.find(searchQueryP);
                result = "";
                while (cursorP.hasNext()) {
                    DBObject nextDocument = cursorP.next();
                    String detailsObj = nextDocument.toString();
                    result += detailsObj + "\n";
                }
                
                
            }
           
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return Response.status(201).entity(result).build();
    }
    
    @GET
    @Path("/getscoregame/{gameID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readScoreGame(@PathParam("gameID") String gameID) {
        String result = "";
        //System.out.println("797 gameID \t"+gameID);
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            //System.out.println("802 gameID \t"+gameID);
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("finalscoredetails");
            //System.out.println("807 gameID \t"+gameID);
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("gameID", gameID);
            DBCursor cursor = table.find(searchQuery);
            result = "{\"gameFinalScore\": [";
            //String strTime = "";
            //System.out.println("815 gameID \t"+gameID);
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString(); 
                String subDetails = "{"; 
                subDetails +=	details.substring(51, details.length());
                //System.out.println("821 gameID \t"+gameID);
                
               	
                result += subDetails + "\n";
                result += ",";
                //System.out.println("826 gameID \t"+gameID);
                 
                
            }
            //
            //System.out.println("831 gameID \t"+gameID);
            result=result.substring(0, result.length() - 1);
            result +="]}";    
            //.out.println("834 gameID \t"+gameID);
            mongoClient.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // sending the updated records back to the client
        //System.out.println("844 gameID \t"+gameID);
        return Response.status(201).entity(result).build();
        
    }
    
    @GET
    @Path("/getfinalscoreplayer/{playerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readScore(@PathParam("playerName") String playerName) {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("finalscoredetails");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("playerName", playerName);
            DBCursor cursor = table.find(searchQuery);
            result = "{\"gamefinalscoreplayer\": [";
            //String strTime = "";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString(); 
                String subDetails = "{"; 
                subDetails +=	details.substring(51, details.length());
                
               	
                result += subDetails + "\n";
                result += ",";
                	
                 
                
            }
            //
            result=result.substring(0, result.length() - 1);
            result +="]}";            
            mongoClient.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // sending the updated records back to the client
        return Response.status(201).entity(result).build();
        
    }
    
    @GET
    @Path("/getfinalscore")
    @Produces(MediaType.APPLICATION_JSON)
    public String readAllFinalScore() {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("finalscoredetails");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            //searchQuery.put("registrationID", registrationID);
            DBCursor cursor = table.find();
            //Location loc= null;
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                //loc= new Location();
            }
            mongoClient.close();
            /*Location curLoc = new Location("99",99,99,99,"N",99,"99");
             System.out.println("Is it safe to make a move? " + isSafe(curLoc, locMap));*/
    
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="MongoDB server is inactive";
        }
        
        // sending the updated records back to the client
        return result;
        
    }
    
    @DELETE
    @Path("/deletefinalgamescore/{gameID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deRegisFinalGameScore(@PathParam("gameID") String gameID) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("finalscoredetails");
            BasicDBObject deleteQuery = new BasicDBObject().append("gameID", gameID);
            
            table.remove(deleteQuery);
            
            mongoClient.close();
            // sending the response of deRegister function to the client
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        return Response.status(201).entity(result).build();
    }
    
    
    @DELETE
    @Path("/deletefinalgamescoreplayer/{playerName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deRegisFinalGameScorePlayer(@PathParam("playerName") String playerName) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("game202");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("finalscoredetails");
            BasicDBObject deleteQuery = new BasicDBObject().append("playerName", playerName);
            
            table.remove(deleteQuery);
            
            mongoClient.close();
            // sending the response of deRegister function to the client
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        return Response.status(201).entity(result).build();
    }
    
    // ++ ND 89
    /*
    @POST
    @Path("/postrating")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerRating(Street street ) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017 collisionPrevention
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("rateStreet");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("street");
            
            
            
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("streetName", street.getStreetName());
            DBCursor cursor = table.find(searchQuery);
            String strTime = "";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                
                // ++ ND
                strTime = (String) nextDocument.get("streetName");
                // -- ND
                
            }
            if(strTime.equals(""))
            {
                //System.out.println("The record is not yet inserted at \t\t" + strTime );
                BasicDBObject document = new BasicDBObject();
                document.put("streetName", street.getStreetName());
                document.put("rating", street.getRating());
                
                table.insert(document);
                //System.out.println("Person will be going ====>"+clientID);
                
                // searching the DB to get the document for the given client
                BasicDBObject searchQueryP = new BasicDBObject();
                searchQueryP.put("streetName", street.getStreetName());
                DBCursor cursorP = table.find(searchQueryP);
                result = "";
                while (cursorP.hasNext()) {
                    DBObject nextDocument = cursorP.next();
                    String detailsObj = nextDocument.toString();
                    result += detailsObj + "\n";
                }
                
                
            }
            else {
                //System.out.println("The record was inserted at \t\t" + strTime );
                BasicDBObject updatedClient = new BasicDBObject();
                updatedClient.put("streetName", street.getStreetName());
                updatedClient.put("rating", street.getRating());
                BasicDBObject updateQuery = new BasicDBObject().append("streetName", street.getStreetName());
                
                table.update(updateQuery, updatedClient);
                
                // searching the DB to retrieve the updated details of the client
                BasicDBObject searchQueryPut = new BasicDBObject();
                searchQueryPut.put("streetName", street.getStreetName());
                DBCursor cursorPut = table.find(searchQueryPut);
                result = "";
                while (cursorPut.hasNext()) {
                    DBObject nextDocument = cursorPut.next();
                    String details = nextDocument.toString();
                    result += details + "\n";
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return Response.status(201).entity(result).build();
    }
    */
    
    
    // -- ND 89
    
    
    // -- ND 17
    
    // sending the registered client information to the client
    
    /*
     @PUT
     @Path("/put")
     @Consumes(MediaType.APPLICATION_JSON)
     public Response update(Location location) {
     
     String result = "";
     
     
     MongoClient mongoClient;
     try {
     mongoClient = new MongoClient("localhost", 27017);
     
     @SuppressWarnings("deprecation")
     DB db = mongoClient.getDB("beacon");
     // gets collection user..similar to table
     DBCollection table = db.getCollection("victimDetails");
     
     
     BasicDBObject updatedClient = new BasicDBObject();
     updatedClient.put("clientID", location.getClientID());
     updatedClient.put("latitude", location.getLatitude());
     updatedClient.put("longitude", location.getLongitude());
     updatedClient.put("timeStamp", location.getTimeStamp());
     BasicDBObject updateQuery = new BasicDBObject().append("clientID", location.getClientID());
     
     table.update(updateQuery, updatedClient);
     
     // searching the DB to retrieve the updated details of the client
     BasicDBObject searchQuery = new BasicDBObject();
     searchQuery.put("clientID", location.getClientID());
     DBCursor cursor = table.find(searchQuery);
     while (cursor.hasNext()) {
     DBObject nextDocument = cursor.next();
     String details = nextDocument.toString();
     result += details + "\n";
     }
     } catch (UnknownHostException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }
     // sending the updated records back to the client
     return Response.status(201).entity(result).build();
     }
     */
    
    /*
     // ND ++ get post
     @GET
     @Path("/get/{clientID}/{latitude}/{longitude}/{timeStamp}")
     @Consumes(MediaType.APPLICATION_JSON)
     public String getRegisterPost(@PathParam("clientID") String clientID, @PathParam("latitude") String latitude, @PathParam("longitude") String longitude, @PathParam("timeStamp") String timeStamp) {
     
     String result = "";
     
     // NDND ++
     
     // Creating serverside mongoDB which is listening on port 27017
     MongoClient mongoClient;
     try {
     mongoClient = new MongoClient("localhost", 27017);
     
     @SuppressWarnings("deprecation")
     DB db = mongoClient.getDB("beacon");
     // gets collection user..similar to table
     DBCollection table = db.getCollection("victimDetails");
     
     // searching the DB to retrieve the updated details of the client
     BasicDBObject searchQuery = new BasicDBObject();
     searchQuery.put("clientID", clientID);
     DBCursor cursor = table.find(searchQuery);
     String strTime = "";
     while (cursor.hasNext()) {
     DBObject nextDocument = cursor.next();
     String details = nextDocument.toString();
     result += details + "\n";
     
     // ++ ND
     strTime = (String) nextDocument.get("timeStamp");
     // -- ND
     
     }
     if(strTime.equals(""))
     {
     System.out.println("The record is not yet inserted at \t\t" + strTime );
     BasicDBObject document = new BasicDBObject();
     document.put("clientID", clientID);
     document.put("latitude", Double.parseDouble(latitude));
     document.put("longitude", Double.parseDouble(longitude));
     document.put("timeStamp", timeStamp);
     
     table.insert(document);
     //System.out.println("Person will be going ====>"+clientID);
     
     // searching the DB to get the document for the given client
     BasicDBObject searchQueryP = new BasicDBObject();
     searchQueryP.put("clientID", clientID);
     DBCursor cursorP = table.find(searchQueryP);
     result = "";
     while (cursorP.hasNext()) {
					DBObject nextDocument = cursorP.next();
					String detailsObj = nextDocument.toString();
					result += detailsObj + "\n";
     }
     
     
     }
     else if(checkDate(timeStamp, strTime)){
     System.out.println("The record was inserted at \t\t" + strTime );
     BasicDBObject updatedClient = new BasicDBObject();
     updatedClient.put("clientID", clientID);
     updatedClient.put("latitude", Double.parseDouble(latitude));
     updatedClient.put("longitude", Double.parseDouble(longitude));
     updatedClient.put("timeStamp", timeStamp);
     BasicDBObject updateQuery = new BasicDBObject().append("clientID", clientID);
     
     table.update(updateQuery, updatedClient);
     
     // searching the DB to retrieve the updated details of the client
     BasicDBObject searchQueryPut = new BasicDBObject();
     searchQueryPut.put("clientID", clientID);
     DBCursor cursorPut = table.find(searchQueryPut);
     result = "";
     while (cursorPut.hasNext()) {
					DBObject nextDocument = cursorPut.next();
					String details = nextDocument.toString();
					result += details + "\n";
     }
     }
     } catch (UnknownHostException e) {
     e.printStackTrace();
     }
     
     
     // NDND --
     
     
     
     String response ="";
     
     
     response = "callbackMethod ( {\"success\" : \"true\"});";
     
     
     // sending the registered client information to the client ObjectMapper
     return response;
     }
     */
    public static boolean checkDate(String dateS1, String dateS2) {
        boolean res = false;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //java.util.Date date1;
        //java.util.Date date2;
        long d1 =0L;
        long d2 =0L;
        try {
            d1= Long.parseLong(dateS1);
            d2=Long.parseLong(dateS2);
            if(d1>d2){
                res=true;
            }
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }
    // ND -- get post
    
    
    // ND ++ get put
    /*	@GET
     @Path("/share/{clientID}/{eventID}/{latitude}/{longitude}/{isAgree}/{contactInfo}")
     @Consumes(MediaType.APPLICATION_JSON)
     public String getRegister(@PathParam("clientID") String clientID, @PathParam("eventID") String eventID, @PathParam("latitude") String latitude, @PathParam("longitude") String longitude, @PathParam("isAgree") String isAgree, @PathParam("contactInfo") String contactInfo) {
     
     String result = "";
     
     // Creating serverside mongoDB which is listening on port 27017
     MongoClient mongoClient;
     try {
     mongoClient = new MongoClient("localhost", 27017);
     
     @SuppressWarnings("deprecation")
     DB db = mongoClient.getDB("beacon");
     // gets collection user..similar to table
     DBCollection table = db.getCollection("victimLocation");
     
     System.out.println("Person interested in sharing==>"+clientID);
     
     // create a row (document) in collection based on the object sent from
     // post call
     BasicDBObject document = new BasicDBObject();
     document.put("clientID", clientID);
     document.put("eventID", eventID);
     document.put("latitude", Double.parseDouble(latitude));
     document.put("longitude", Double.parseDouble(longitude));
     document.put("isAgree", Boolean.parseBoolean(isAgree));
     document.put("contactInfo", contactInfo);
     
     
     BasicDBObject updateQuery = new BasicDBObject().append("clientID", clientID);
     
     table.update(updateQuery, document);
     
     
     // searching the DB to get the document for the given client
     BasicDBObject searchQuery = new BasicDBObject();
     searchQuery.put("clientID", clientID);
     DBCursor cursor = table.find(searchQuery);
     while (cursor.hasNext()) {
     DBObject nextDocument = cursor.next();
     String detailsObj = nextDocument.toString();
     result += detailsObj + "\n";
     }
     } catch (UnknownHostException e) {
     e.printStackTrace();
     }
     
     String response ="";
     
     
     response = "callbackMethod ( {\"success\" : \"true\"});";
     
     
     // sending the registered client information to the client ObjectMapper
     return response;
     }
     */
    // ND -- get put
    
    @GET
    @Path("/get/{streetName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("streetName") String streetName) {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("rateStreet");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("incident");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("streetName", streetName);
            DBCursor cursor = table.find(searchQuery);
            //String strTime = "";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                
            }
            mongoClient.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        // sending the updated records back to the client
        return Response.status(201).entity(result).build();
        
    }
    
    // Get all
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public String readAll() {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("rateStreet");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("incident");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            //searchQuery.put("registrationID", registrationID);
            DBCursor cursor = table.find();
            Location loc= null;
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                loc= new Location();
            }
            mongoClient.close();
            /*Location curLoc = new Location("99",99,99,99,"N",99,"99");
             System.out.println("Is it safe to make a move? " + isSafe(curLoc, locMap));*/
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="MongoDB server is inactive";
        }
        
        // sending the updated records back to the client
        return result;
        
    }
    
    // ++ ND 89
    
    @GET
    @Path("/getrating/{rating}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readStreetRating(@PathParam("rating") String rating) {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("rateStreet");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("street");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("rating", rating);
            DBCursor cursor = table.find(searchQuery);
            //String strTime = "";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                
            }
            mongoClient.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        // sending the updated records back to the client
        return Response.status(201).entity(result).build();
        
    }
    
    //
    
    @GET
    @Path("/getstreet/{streetName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readStreet(@PathParam("streetName") String streetName) {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("rateStreet");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("street");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("streetName", streetName);
            DBCursor cursor = table.find(searchQuery);
            //String strTime = "";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                
            }
            mongoClient.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        // sending the updated records back to the client
        return Response.status(201).entity(result).build();
        
    }
    
    // Get all
    @GET
    @Path("/getrating")
    @Produces(MediaType.APPLICATION_JSON)
    public String readAllStreet() {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("rateStreet");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("street");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            //searchQuery.put("registrationID", registrationID);
            DBCursor cursor = table.find();
            Location loc= null;
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details + "\n";
                loc= new Location();
            }
            mongoClient.close();
            /*Location curLoc = new Location("99",99,99,99,"N",99,"99");
             System.out.println("Is it safe to make a move? " + isSafe(curLoc, locMap));*/
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="MongoDB server is inactive";
        }
        
        // sending the updated records back to the client
        return result;
        
    }
    
    
    //  -- ND 89
    
    
    // ND get json ++
    
    // Get all
    @GET
    @Path("/getjson")
    @Produces(MediaType.APPLICATION_JSON)
    public String readAlljson() {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("beacon");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("victimDetails");
            
            // searching the DB to retrieve the updated details of the client
            BasicDBObject searchQuery = new BasicDBObject();
            //searchQuery.put("registrationID", registrationID);
            DBCursor cursor = table.find();
            Location loc= null;
            result = "{\"ll\": [";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                result += details;
                result += ",";
                /*loc.setRegistrationID((String) nextDocument.get("registrationID"));
                 loc.setLatitude((Double) nextDocument.get("latitude"));
                 loc.setLongitude((Double) nextDocument.get("longitude"));
                 loc.setAltitude((Double) nextDocument.get("altitude"));
                 loc.setDirection((String) nextDocument.get("direction"));
                 loc.setSpeed((Double) nextDocument.get("speed"));
                 locMap.put((String) nextDocument.get("registrationID"), loc);
                 loc = null;
                 */
                //					Object reg1 = nextDocument.get("registrationID");
                //					Object reg2 = nextDocument.get("latitude");
                //					Object reg3 = nextDocument.get("longitude");
                //					System.out.println(reg1.toString());
                //					System.out.println("Value of 2 is: "+reg2.toString());
                //					System.out.println("Value of 3 is: "+reg3.toString());
            }
            result=result.substring(0, result.length() - 1);
            result +="]}";
            mongoClient.close();
            /*Location curLoc = new Location("99",99,99,99,"N",99,"99");
             System.out.println("Is it safe to make a move? " + isSafe(curLoc, locMap));*/
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="MongoDB server is inactive";
        }
        
        // sending the updated records back to the client
        return result;
        
    }
    
    
    // ND get json --
    
    
    // ++ ND
    /*
     // Get all
     @GET
     @Path("/get/log")
     @Produces(MediaType.APPLICATION_JSON)
     public String readlog() {
     String result = "";
     
     // Creating serverside mongoDB which is listening on port 27017
					MongoClient mongoClient;
     
					File dir = new File("/home/ec2-user/extractEvents/logs");
     
					// Tests whether the directory denoted by this abstract pathname exists.
					if(dir.exists()){
     result="Logging is successful.";
					}
					else {
     result="Logging is unsuccessful, please get the directory created";
					}
					return result;
     }
     */
    
    // -- ND
    
    // Get all nearby
    @GET
    @Path("/nearby/{latitude}/{longitude}")
    @Produces(MediaType.APPLICATION_JSON)
    public String readNearby(@PathParam("latitude") String latitude,@PathParam("longitude") String longitude) {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        double latit = Double.parseDouble(latitude);
        double longi = Double.parseDouble(longitude);
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("beacon");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("victimDetails");
            
            // searching the DB to retrieve the updated details of the client
            DBCursor cursor = table.find();
            double latitude1 = 0.0;
            double longitude1 = 0.0;
            
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                latitude1 =  Double.parseDouble((String) nextDocument.get("lat"));
                longitude1 = Double.parseDouble((String) nextDocument.get("lon"));
                
                if (isNear(latit, longi,latitude1, longitude1)){
                    result += details + "\n";
                }
            }
            mongoClient.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    
    
    //  Nearby Json ++
    
    @GET
    @Path("/nearbyjson/{latitude}/{longitude}")
    @Produces(MediaType.APPLICATION_JSON)
    public String readNearbyJson(@PathParam("latitude") String latitude,@PathParam("longitude") String longitude) {
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        double latit = Double.parseDouble(latitude);
        double longi = Double.parseDouble(longitude);
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("beacon");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("victimDetails");
            
            // searching the DB to retrieve the updated details of the client
            DBCursor cursor = table.find();
            double latitude1 = 0.0;
            double longitude1 = 0.0;
            result = "{\"ll\": [";
            while (cursor.hasNext()) {
                DBObject nextDocument = cursor.next();
                String details = nextDocument.toString();
                latitude1 =  Double.parseDouble((String) nextDocument.get("lat"));
                longitude1 = Double.parseDouble((String) nextDocument.get("lon"));
                
                if (isNear(latit, longi,latitude1, longitude1)){
                    result += details;
                    result += ",";
                }
            }
            result=result.substring(0, result.length() - 1);
					       
            
            result +="]}";
            mongoClient.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    
    
    
    // Nearby JSON --
    
    @DELETE
    @Path("/delete/{streetName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deRegister(@PathParam("streetName") String streetName) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("rateStreet");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("incident");
            BasicDBObject deleteQuery = new BasicDBObject().append("streetName", streetName);
            
            table.remove(deleteQuery);
            
            mongoClient.close();
            // sending the response of deRegister function to the client
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        return Response.status(201).entity(result).build();
    }
    
    
    @DELETE
    @Path("/deletestreet/{streetName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deRegisterStreet(@PathParam("streetName") String streetName) {
        
        String result = "";
        
        // Creating serverside mongoDB which is listening on port 27017
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient("localhost", 27017);
            
            @SuppressWarnings("deprecation")
            DB db = mongoClient.getDB("rateStreet");
            // gets collection user..similar to table
            DBCollection table = db.getCollection("street");
            BasicDBObject deleteQuery = new BasicDBObject().append("streetName", streetName);
            
            table.remove(deleteQuery);
            
            mongoClient.close();
            // sending the response of deRegister function to the client
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        return Response.status(201).entity(result).build();
    }
    
    // ND + Distance calculator
    /*
     public static boolean isSafe(Location currentLoc, Map<String, Location> locMap) {
     double safeDistance=6;
     boolean status=true;
     
     
     for(String iterLocation: locMap.keySet()){
     
     if(!iterLocation.equals(currentLoc.getRegistrationID())){
     double dis = distance(currentLoc.getLatitude(), currentLoc.getLongitude(),locMap.get(iterLocation).getLatitude() , locMap.get(iterLocation).getLatitude());
     System.out.println(dis);
     if(dis>safeDistance && dis > 1){
					status = true;
     }
     else {
					status = false;
					return status;
     }
     }
     
     
     }
     
     return status;
     
     
     }*/
    
    public boolean isNear(double lat1, double lon1, double lat2, double lon2) {
        if((distance(lat1, lon1, lat2, lon2))<10) {
            return true;
        }
        return false;
    }
    
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }
    
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts decimal degrees to radians						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts radians to decimal degrees						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    
    // ND -- Distance
}

