package view;

import javax.swing.*;

/**
 * Created by ghoskno on 4/2/17.
 */
public class BookJTable extends JTable {
    public BookJTable(int row,int col){
       super();
    }

    public void setCellEditable(int row,int col){
        for(int r=0;r<=row;r++)
            for (int c=0;c<col;c++){
                this.isCellEditable(r,c);
            }

    }
    public boolean isCellEditable(int row, int column) {				// 表格编辑
        return false;
    }
}
