package structures;

import java.util.LinkedList;

/**
 *
 * @author aurea
 */
public class SparseMatrix {

     LinkedList<LinkedList<Data>> matrix;
    
    
 

    public SparseMatrix() {
        matrix = new LinkedList<LinkedList<Data>>();
    }

    public LinkedList<LinkedList<Data>> getMatrix()
    {
        return matrix;
    }
    public boolean insert(Data data) {
        int row = findRow(data);
        int col = findCol(data, row);

        try {
            Data element = matrix.get(row).get(0);

            /*
             * Si el primer elemento de la fila tiene la misma row que nuestro elemento a ingresar
             */
            if (element.compareTo(data, "row") == 0) {
                try {
                    element = matrix.get(row).get(col);

                    /*
                     * Si el dato existe aumenta su sobreposicion
                     */
                    if (element.compareTo(data, "col") == 0) {
                        matrix.get(row).get(col).sobrepositions++;
                        return false;
                    } /*
                     * si el dato no existe lo agregamos en su posicion
                     */ else {
                        matrix.get(row).add(col, data);
                        return true;
                    }
                } catch (Exception e1) {
                    matrix.get(row).add(data);
                    return true;
                }
            } else {
                LinkedList<Data> list = new LinkedList<Data>();
                list.add(data);
                matrix.add(row, list);
                return true;
            }

        } catch (Exception e) {
            LinkedList<Data> list = new LinkedList<Data>();
            list.add(data);
            try {
                if (row < matrix.size() && row != 0) {
                    matrix.add(row, list);
                    return true;
                } else {
                    matrix.add(list);
                    return true;
                }
            } catch (Exception e2) {
                matrix.add(list);
                return true;
            }
        }
  
    }

    Integer findRow(Data data) {
        int i = 0;
        try {
            for (i = 0; i < matrix.size(); i++) {
                Data element = matrix.get(i).get(0);
                if (element.compareTo(data, "row") >= 0) {
                    return i;
                }

            }
            return i;
        } catch (Exception e) {
            return i;
        }
    }

    Integer findCol(Data data, Integer row) {
        try {
            if (!matrix.get(row).get(0).row.equals(data.row)) {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
        int i = 0;
        for (i = 0; i < matrix.get(row).size(); i++) {
            Data element = matrix.get(row).get(i);
          
            if (element.compareTo(data, "col") >= 0) {
                return i;
            }

        }
        return i;
    }

    
    void print()
    {
        for(int i = 0;i < matrix.size(); i++)
        {
            for(int j = 0 ;j < matrix.get(i).size(); j++)
            {
                System.out.print("("+matrix.get(i).get(j).row+","+matrix.get(i).get(j).col+"):"+matrix.get(i).get(j).sobrepositions+" ");
            }
            System.out.println();
        }
    }
}