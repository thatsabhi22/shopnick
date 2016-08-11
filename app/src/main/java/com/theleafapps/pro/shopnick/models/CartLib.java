//package com.theleafapps.pro.shopnick.models;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
///**
// * Created by aviator on 20/06/16.
// *
// * A representation of shopping cart.
// */
//public class Cart {
//
//    private BigDecimal totalPrice = BigDecimal.ZERO;
//    private int totalQuantity = 0;
//
//    /**
//    * Add a quantity of product to this shopping cart
//    */
//    public void add(final Saleable sellable, int quantity) {
//
//    }
//
//    /**
//     * Set new quantity for a product in this shopping cart
//     */
//    public void add(final Saleable sellable, int quantity) {
//
//    }
//
//    /**
//    * Remove a certain quantity of a {@link Saleable} product from this shopping cart
//    */
//    public void remove(final Saleable sellable, int quantity){
//
//    }
//
//    /**
//     * Remove all products from this shopping cart
//     */
//    public void clear() {
//        cartItemMap.clear();
//        totalPrice = BigDecimal.ZERO;
//        totalQuantity = 0;
//    }
//
//    /**
//     * Get quantity of a {@link Saleable} product in this shopping cart
//     *
//     * @param sellable the product of interest which this method will return the quantity
//     * @return The product quantity in this shopping cart
//     * @throws ProductNotFoundException if the product is not found in this shopping cart
//     */
//    public int getQuantity(final Saleable sellable) throws ProductNotFoundException {
//        if (!cartItemMap.containsKey(sellable)) throw new ProductNotFoundException();
//        return cartItemMap.get(sellable);
//    }
//
//    /**
//     * Get total cost of a {@link Saleable} product in this shopping cart
//     *
//     * @param sellable the product of interest which this method will return the total cost
//     * @return Total cost of the product
//     * @throws ProductNotFoundException if the product is not found in this shopping cart
//     */
//    public BigDecimal getCost(final Saleable sellable) throws ProductNotFoundException {
//        if (!cartItemMap.containsKey(sellable)) throw new ProductNotFoundException();
//        return sellable.getPrice().multiply(BigDecimal.valueOf(cartItemMap.get(sellable)));
//    }
//
//    /**
//     * Get total price of all products in this shopping cart
//     *
//     * @return Total price of all products in this shopping cart
//     */
//    public BigDecimal getTotalPrice() {
//        return totalPrice;
//    }
//
//    /**
//     * Get total quantity of all products in this shopping cart
//     *
//     * @return Total quantity of all products in this shopping cart
//     */
//    public int getTotalQuantity() {
//        return totalQuantity;
//    }
//
//    /**
//     * Get set of products in this shopping cart
//     *
//     * @return Set of {@link Saleable} products in this shopping cart
//     */
//    public Set<Saleable> getProducts() {
//        return cartItemMap.keySet();
//    }
//
//    /**
//     * Get a map of products to their quantities in the shopping cart
//     *
//     * @return A map from product to its quantity in this shopping cart
//     */
//    public Map<Saleable, Integer> getItemWithQuantity() {
//        Map<Saleable, Integer> cartItemMap = new HashMap<Saleable, Integer>();
//        cartItemMap.putAll(this.cartItemMap);
//        return cartItemMap;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder strBuilder = new StringBuilder();
//        for (Map.Entry<Saleable, Integer> entry : cartItemMap.entrySet()) {
//            strBuilder.append(String.format("Product: %s, Unit Price: %f, Quantity: %d%n", entry.getKey().getName(), entry.getKey().getPrice(), entry.getValue()));
//        }
//        strBuilder.append(String.format("Total Quantity: %d, Total Price: %f", totalQuantity, totalPrice));
//
//        return strBuilder.toString();
//    }
//}
