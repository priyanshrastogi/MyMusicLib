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
public class AlbumModel extends AbstractTableModel {

    private static final int ALBUM_NAME = 0;
    private static final int ARTIST_NAME = 1;
    private static final int GENRE = 2;
    private static final int YEAR = 3;
    private static final int ALBUM_ID = 4;
    
    private String[] columnNames = {"Album","Artist","Genre","Year"};
    private List<Album> albums;
    
    public AlbumModel(List<Album> theAlbums) {
        this.albums = theAlbums;
    }
    
    @Override
    public int getRowCount() {
        return albums.size(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        return columnNames.length; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Album album = albums.get(rowIndex);
        switch(columnIndex) {
            case ALBUM_ID:
                return album.getAlbumID();
            case ALBUM_NAME:
                return album.getAlbumName();
            case ARTIST_NAME:
                return album.getArtist();
            case GENRE:
                return album.getGenre();
            case YEAR:
                return album.getYear();
            default:
                return album.getAlbumName();
                
        }
    }
    
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0,c).getClass();
    }
    
}
