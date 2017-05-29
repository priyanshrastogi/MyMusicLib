/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymusiccloud;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Priyansh Rastogi
 */
public class SongModel extends AbstractTableModel{
    
    private static final int SONG_NAME = 0;
    private static final int ALBUM_ID = 3;
    private static final int PATH = 1;
    private static final int DELETE = 2;
    private static final int SONG_ID = 4;
    
    private String[] columnNames = {"Song","Path","Action"};
    private List<Song> songs;
    private String action = "Delete";
    
    public SongModel(List<Song> songs) {
        this.songs = songs;
    }
    
    @Override
    public int getRowCount() {
        return songs.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Song song = songs.get(rowIndex);
        switch(columnIndex) {
            case SONG_NAME:
                return song.getSongName();
            case ALBUM_ID:
                return song.getAlbumID();
            case PATH:
                return song.getPath();
            case DELETE:
                return action;
            case SONG_ID:
                return song.getSongID();
            default:
                return song.getSongName();
                
        }
    }
    
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0,c).getClass();
    }
    
}
