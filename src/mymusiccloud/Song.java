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
public class Song {
    
    private int songID;
    private String songName;
    private int albumID;
    private String path;
    
    public Song(int id, String song, int albumID, String path) {
        this.songID = id;
        this.songName = song;
        this.albumID = albumID;
        this.path = path;
    }
    
    public String getSongName() {
        return songName;
    }
    
    public int getAlbumID() {
        return albumID;
    }
    
    public String getPath() {
        return path;
    }
    
    public int getSongID() {
        return songID;
    }
}
