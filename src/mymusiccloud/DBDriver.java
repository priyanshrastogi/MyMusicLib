package mymusiccloud;


import java.io.FileInputStream;
import java.sql.*;
import java.util.*;
import org.jasypt.util.password.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Priyansh Rastogi
 */


public class DBDriver {

    private Connection myConn;
	
    public DBDriver() throws Exception {
	
        Properties props = new Properties();
        props.load(new FileInputStream("database.properties"));

        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String dburl = props.getProperty("dburl");

        // connect to database
        myConn = DriverManager.getConnection(dburl, user, password);

        System.out.println("DB connection successful to: " + dburl);
    }

    public List<Album> getAllAlbums(String user) throws Exception {
	List<Album> list = new ArrayList<Album>();
		
	PreparedStatement myStmt = null;
	ResultSet myRs = null;
        try {
            myStmt = myConn.prepareStatement("select * from albums where user=?");
            myStmt.setString(1, user);
            myRs = myStmt.executeQuery();
			
            while (myRs.next()) {
                Album album = convertRowToAlbum(myRs);
                list.add(album);
            }

            return list;		
	}
	finally {
            close(myStmt, myRs);
	}
    }
    
    public List<Song> getAllSongs(int albumID) throws Exception {
	List<Song> list = new ArrayList<Song>();
		
	PreparedStatement myStmt = null;
	ResultSet myRs = null;
        try {
            myStmt = myConn.prepareStatement("select * from songs where album_id=?");
            myStmt.setInt(1, albumID);
            myRs = myStmt.executeQuery();
			
            while (myRs.next()) {
                Song song = convertRowToSong(myRs);
                list.add(song);
            }

            return list;		
	}
	finally {
            close(myStmt, myRs);
	}
    }
    
    public User auth(String username, String password) throws SQLException{
        User authUser = null;
        PreparedStatement myStmt = null;
	ResultSet myRs = null;
        String encPassword = null;
        try {
            myStmt = myConn.prepareStatement("select * from users where email=?");
            myStmt.setString(1, username);
            myRs = myStmt.executeQuery();
			
            while (myRs.next()) {
                encPassword = myRs.getString("password");
            }
	
	}
	catch(Exception e) {
            e.printStackTrace();
	}
        
        if(!myRs.first()) {
            System.out.println("No User Found");
            close(myStmt,myRs);
            return authUser;
        }
        
        else if(checkPassword(password, encPassword)) {
            authUser = convertRowToUser(myRs);
            close(myStmt,myRs);
            return authUser;
        }
        
        else {
            close(myStmt,myRs);
            return authUser;
        }
    }
    
    private Album convertRowToAlbum(ResultSet myRs) throws SQLException {
		
	int albumID = myRs.getInt("id");
        String albumName = myRs.getString("name");
        String artist = myRs.getString("artist");
        String genre = myRs.getString("genre");
        String year = myRs.getString("year");
        String user = myRs.getString("user");

        Album album = new Album(albumID, albumName, artist, genre, year, user);

        return album;
    }
    
    private User convertRowToUser(ResultSet myRs) throws SQLException {
		
	String name = myRs.getString("name");
        String email = myRs.getString("email");
        String password = myRs.getString("password");

        User user = new User(name, email, password);
        return user;
    }
    
    private Song convertRowToSong(ResultSet myRs) throws SQLException {
		
	int id = myRs.getInt("id");
        String name = myRs.getString("name");
        int albumID = myRs.getInt("album_id");
        String path = myRs.getString("path");

        Song song = new Song(id, name, albumID, path);
        return song;
    }

    public void addAlbum(Album album) throws Exception {
        PreparedStatement myStmt = null;

        try {
            // prepare statement
            myStmt = myConn.prepareStatement("insert into albums values (?, ?, ?, ?, ?, ?)");

            // set params
            myStmt.setNull(1, java.sql.Types.INTEGER);
            myStmt.setString(2, album.getAlbumName());
            myStmt.setString(3, album.getArtist());
            myStmt.setString(4, album.getGenre());
            myStmt.setString(5, album.getYear());
            myStmt.setString(6, album.getUser());

            myStmt.executeUpdate();
        }finally {
            close(myStmt);
        }
    }
    
    public void deleteAlbum(int albumID) throws Exception {
        PreparedStatement myStmt = null;

        try {
            // prepare statement
            myStmt = myConn.prepareStatement("delete from albums where id=?");

            // set params
            myStmt.setInt(1,albumID);

            myStmt.executeUpdate();
        }finally {
            close(myStmt);
        }
    }
    
    public void addSong(Song song) throws Exception {
        PreparedStatement myStmt = null;

        try {
            // prepare statement
            myStmt = myConn.prepareStatement("insert into songs values (?, ?, ?, ?)");

            // set params
            myStmt.setNull(1, java.sql.Types.INTEGER);
            myStmt.setString(2, song.getSongName());
            myStmt.setInt(3, song.getAlbumID());
            myStmt.setString(4, song.getPath());

            myStmt.executeUpdate();
        }finally {
            close(myStmt);
        }
    }
    
    public void deleteSong(int songID) throws Exception {
        PreparedStatement myStmt = null;

        try {
            // prepare statement
            myStmt = myConn.prepareStatement("delete from songs where id=?");

            // set params
            myStmt.setInt(1,songID);

            myStmt.executeUpdate();
        }finally {
            close(myStmt);
        }
    }
    
    public void addUser(User user) throws Exception {
        PreparedStatement myStmt = null;

        try {
            // prepare statement
            myStmt = myConn.prepareStatement("insert into users values (?, ?, ?)");

            // set params
            myStmt.setString(1, user.getEmail());
            myStmt.setString(2, user.getName());
            myStmt.setString(3, encryptPassword(user.getPassword()));

            // execute SQL
            myStmt.executeUpdate();
        }finally {
            close(myStmt);
        }
    }
    
    
	
    private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {

        if (myRs != null) {
            myRs.close();
        }

        if (myStmt != null) {

            }

        if (myConn != null) {
            myConn.close();
        }
    }
    
    public String encryptPassword(String password) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
	String encryptedPass = passwordEncryptor.encryptPassword(password);
        return encryptedPass;
    }
    
    public boolean checkPassword(String plainText, String encryptedPassword) {
	StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        return passwordEncryptor.checkPassword(plainText, encryptedPassword);
    }

    private void close(Statement myStmt, ResultSet myRs) throws SQLException {
            close(null, myStmt, myRs);		
    }
    
    public static void close(Statement myStmt) throws SQLException {
		close(null, myStmt, null);		
	}
    
    public static void main(String[] args) throws Exception {
		
		DBDriver driver = new DBDriver();
    }
}
