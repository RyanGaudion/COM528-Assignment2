package org.solent.com504.oodd.cart.model.dto;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Invoice {

    private Long id;

    private String invoiceNumber;

    private Date dateOfPurchase;

    private Double amountDue;
    
    private InvoiceStatus invoiceStatus;

    private List<OrderItem> purchasedItems;

    private User purchaser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvoiceStatus getInvoiceStatus(){
        return invoiceStatus;
    }
    
    public void setInvoiceStatus(InvoiceStatus status){
        this.invoiceStatus = status;
    }
    
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    @OneToMany(cascade = CascadeType.PERSIST) 
    public List<OrderItem> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<OrderItem> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    @OneToOne
    public User getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
    }

}
