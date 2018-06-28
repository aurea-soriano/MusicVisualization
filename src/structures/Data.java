package structures;

/**
 *
 * @author aurea
 */
public class Data {
    Integer row;
    Integer col;
    Integer sobrepositions;
    Float splatValue;
    
    public Data(Integer row, Integer col)
    {
        this.row = row;
        this.col = col;
        this.sobrepositions = 1;
        this.splatValue = 0.f;
    }
    public Integer getRow()
    {
        return row;
    }
    public Integer getCol()
    {
        return col;
    }
    public Integer getSobrepositions()
    {
        return sobrepositions;
    }
    public Float getSplatValue()
    {
        return splatValue;
    }
      public void setSplatValue(Float splatValue)
    {
        this.splatValue = splatValue;
    }
    Integer compareTo(Data data, String axis)
    {
        if(axis.equals("row"))
        {
              return(this.row.compareTo(data.row));
        }
        return(this.col.compareTo(data.col));
    }
    
    boolean equals(Data data)
    {
        return ((this.row.equals(data.row))&& (this.col.equals(data.col)));
    }
    
  
}
