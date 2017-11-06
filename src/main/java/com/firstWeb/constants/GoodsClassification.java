public enum GoodsClassification
{
    /**
     * 带确认
     */
    waiteConfirm(0),
    /**
     * 客户确认可以解决问题
     */
    resolved(1);
    
    private int value;
    
    GoodsClassification(int value)
    {
        this.value = value;
    }
    
    public int getValue()
    {
        return this.value;
    }
}
