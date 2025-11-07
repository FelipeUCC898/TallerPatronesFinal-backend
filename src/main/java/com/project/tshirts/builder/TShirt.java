package com.project.tshirts.builder;

import java.math.BigDecimal;
import java.util.Objects;

public final class TShirt {
    private final String size;
    private final String color;
    private final String print;
    private final String fabric;
    private final BigDecimal price;
    private final String sku;

    private TShirt(TShirtBuilder builder) {
        this.size = builder.size;
        this.color = builder.color;
        this.print = builder.print;
        this.fabric = builder.fabric;
        this.price = builder.price;
        this.sku = builder.sku;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getPrint() {
        return print;
    }

    public String getFabric() {
        return fabric;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSku() {
        return sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TShirt tShirt = (TShirt) o;
        return Objects.equals(sku, tShirt.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }

    @Override
    public String toString() {
        return "TShirt{" +
                "size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", print='" + print + '\'' +
                ", fabric='" + fabric + '\'' +
                ", price=" + price +
                ", sku='" + sku + '\'' +
                '}';
    }

    public static class TShirtBuilder {
        private String size;
        private String color;
        private String print;
        private String fabric;
        private BigDecimal price;
        private String sku;

        public TShirtBuilder size(String size) {
            if (size == null || size.trim().isEmpty()) {
                throw new IllegalArgumentException("Size cannot be null or empty");
            }
            if (!isValidSize(size)) {
                throw new IllegalArgumentException("Invalid size. Must be S, M, L, or XL");
            }
            this.size = size;
            return this;
        }

        public TShirtBuilder color(String color) {
            if (color == null || color.trim().isEmpty()) {
                throw new IllegalArgumentException("Color cannot be null or empty");
            }
            this.color = color;
            return this;
        }

        public TShirtBuilder print(String print) {
            if (print == null || print.trim().isEmpty()) {
                throw new IllegalArgumentException("Print cannot be null or empty");
            }
            this.print = print;
            return this;
        }

        public TShirtBuilder fabric(String fabric) {
            if (fabric == null || fabric.trim().isEmpty()) {
                throw new IllegalArgumentException("Fabric cannot be null or empty");
            }
            if (!isValidFabric(fabric)) {
                throw new IllegalArgumentException("Invalid fabric. Must be cotton, polyester, or blend");
            }
            this.fabric = fabric;
            return this;
        }

        public TShirtBuilder price(BigDecimal price) {
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Price must be greater than zero");
            }
            this.price = price;
            return this;
        }

        public TShirtBuilder sku(String sku) {
            if (sku == null || sku.trim().isEmpty()) {
                throw new IllegalArgumentException("SKU cannot be null or empty");
            }
            this.sku = sku;
            return this;
        }

        public TShirt build() {
            validateCombinations();
            validateRequiredFields();
            return new TShirt(this);
        }

        private void validateRequiredFields() {
            if (size == null) throw new IllegalStateException("Size is required");
            if (color == null) throw new IllegalStateException("Color is required");
            if (print == null) throw new IllegalStateException("Print is required");
            if (fabric == null) throw new IllegalStateException("Fabric is required");
            if (price == null) throw new IllegalStateException("Price is required");
            if (sku == null) throw new IllegalStateException("SKU is required");
        }

        private void validateCombinations() {
            if ("polyester".equalsIgnoreCase(fabric) && "limited edition".equalsIgnoreCase(print)) {
                throw new IllegalArgumentException("Limited edition prints are not available in polyester fabric");
            }
            if ("blend".equalsIgnoreCase(fabric) && "premium".equalsIgnoreCase(print)) {
                throw new IllegalArgumentException("Premium prints are not available in blend fabric");
            }
        }

        private boolean isValidSize(String size) {
            return "S".equalsIgnoreCase(size) || 
                   "M".equalsIgnoreCase(size) || 
                   "L".equalsIgnoreCase(size) || 
                   "XL".equalsIgnoreCase(size);
        }

        private boolean isValidFabric(String fabric) {
            return "cotton".equalsIgnoreCase(fabric) || 
                   "polyester".equalsIgnoreCase(fabric) || 
                   "blend".equalsIgnoreCase(fabric);
        }
    }
}