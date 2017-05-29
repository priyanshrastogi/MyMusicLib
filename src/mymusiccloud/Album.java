/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymusiccloud;

/**
 *
 * @author Priyansh Rastogi
 */
public class Album {
    
    private int albumID;
    private String albumName;
    private String artist;
    private String genre;
    private String year;
    private String user;
    
    public Album() {
        
    }
    
    
    public Album(int id, String album, String artist, String genre, String year, String user) {
        this.albumID = id;
        this.albumName = album;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
        this.user = user;
    }
    
    public int getAlbumID() {
        return albumID;
    }
    
    public void setAlbumID(int id) {
        this.albumID = id;
    }
    
    public String getAlbumName() {
        return albumName;
    }
    
    public void setAlbumName(String album) {
        this.albumName = album;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public void setArtistName(String artist) {
        this.artist = artist;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public String getYear() {
        return year;
    }
    
    public String getUser() {
        return user;
    }
    
}
