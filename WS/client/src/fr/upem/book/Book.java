/**
 * Book.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.upem.book;

public class Book  implements java.io.Serializable {
    private java.lang.String ISBN;

    private java.lang.String author;

    private boolean available;

    private java.lang.String currency;

    private int exemplary;

    private float price;

    private java.lang.String reviews;

    private boolean saleable;

    private java.lang.String summary;

    private java.lang.String title;

    public Book() {
    }

    public Book(
           java.lang.String ISBN,
           java.lang.String author,
           boolean available,
           java.lang.String currency,
           int exemplary,
           float price,
           java.lang.String reviews,
           boolean saleable,
           java.lang.String summary,
           java.lang.String title) {
           this.ISBN = ISBN;
           this.author = author;
           this.available = available;
           this.currency = currency;
           this.exemplary = exemplary;
           this.price = price;
           this.reviews = reviews;
           this.saleable = saleable;
           this.summary = summary;
           this.title = title;
    }


    /**
     * Gets the ISBN value for this Book.
     * 
     * @return ISBN
     */
    public java.lang.String getISBN() {
        return ISBN;
    }


    /**
     * Sets the ISBN value for this Book.
     * 
     * @param ISBN
     */
    public void setISBN(java.lang.String ISBN) {
        this.ISBN = ISBN;
    }


    /**
     * Gets the author value for this Book.
     * 
     * @return author
     */
    public java.lang.String getAuthor() {
        return author;
    }


    /**
     * Sets the author value for this Book.
     * 
     * @param author
     */
    public void setAuthor(java.lang.String author) {
        this.author = author;
    }


    /**
     * Gets the available value for this Book.
     * 
     * @return available
     */
    public boolean isAvailable() {
        return available;
    }


    /**
     * Sets the available value for this Book.
     * 
     * @param available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }


    /**
     * Gets the currency value for this Book.
     * 
     * @return currency
     */
    public java.lang.String getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this Book.
     * 
     * @param currency
     */
    public void setCurrency(java.lang.String currency) {
        this.currency = currency;
    }


    /**
     * Gets the exemplary value for this Book.
     * 
     * @return exemplary
     */
    public int getExemplary() {
        return exemplary;
    }


    /**
     * Sets the exemplary value for this Book.
     * 
     * @param exemplary
     */
    public void setExemplary(int exemplary) {
        this.exemplary = exemplary;
    }


    /**
     * Gets the price value for this Book.
     * 
     * @return price
     */
    public float getPrice() {
        return price;
    }


    /**
     * Sets the price value for this Book.
     * 
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
    }


    /**
     * Gets the reviews value for this Book.
     * 
     * @return reviews
     */
    public java.lang.String getReviews() {
        return reviews;
    }


    /**
     * Sets the reviews value for this Book.
     * 
     * @param reviews
     */
    public void setReviews(java.lang.String reviews) {
        this.reviews = reviews;
    }


    /**
     * Gets the saleable value for this Book.
     * 
     * @return saleable
     */
    public boolean isSaleable() {
        return saleable;
    }


    /**
     * Sets the saleable value for this Book.
     * 
     * @param saleable
     */
    public void setSaleable(boolean saleable) {
        this.saleable = saleable;
    }


    /**
     * Gets the summary value for this Book.
     * 
     * @return summary
     */
    public java.lang.String getSummary() {
        return summary;
    }


    /**
     * Sets the summary value for this Book.
     * 
     * @param summary
     */
    public void setSummary(java.lang.String summary) {
        this.summary = summary;
    }


    /**
     * Gets the title value for this Book.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Book.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ISBN==null && other.getISBN()==null) || 
             (this.ISBN!=null &&
              this.ISBN.equals(other.getISBN()))) &&
            ((this.author==null && other.getAuthor()==null) || 
             (this.author!=null &&
              this.author.equals(other.getAuthor()))) &&
            this.available == other.isAvailable() &&
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency()))) &&
            this.exemplary == other.getExemplary() &&
            this.price == other.getPrice() &&
            ((this.reviews==null && other.getReviews()==null) || 
             (this.reviews!=null &&
              this.reviews.equals(other.getReviews()))) &&
            this.saleable == other.isSaleable() &&
            ((this.summary==null && other.getSummary()==null) || 
             (this.summary!=null &&
              this.summary.equals(other.getSummary()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getISBN() != null) {
            _hashCode += getISBN().hashCode();
        }
        if (getAuthor() != null) {
            _hashCode += getAuthor().hashCode();
        }
        _hashCode += (isAvailable() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        _hashCode += getExemplary();
        _hashCode += new Float(getPrice()).hashCode();
        if (getReviews() != null) {
            _hashCode += getReviews().hashCode();
        }
        _hashCode += (isSaleable() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSummary() != null) {
            _hashCode += getSummary().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Book.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://book.upem.fr", "Book"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ISBN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://book.upem.fr", "ISBN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("author");
        elemField.setXmlName(new javax.xml.namespace.QName("http://book.upem.fr", "author"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("available");
        elemField.setXmlName(new javax.xml.namespace.QName("http://book.upem.fr", "available"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://book.upem.fr", "currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exemplary");
        elemField.setXmlName(new javax.xml.namespace.QName("http://book.upem.fr", "exemplary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("http://book.upem.fr", "price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reviews");
        elemField.setXmlName(new javax.xml.namespace.QName("http://book.upem.fr", "reviews"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saleable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://book.upem.fr", "saleable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("summary");
        elemField.setXmlName(new javax.xml.namespace.QName("http://book.upem.fr", "summary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("http://book.upem.fr", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
