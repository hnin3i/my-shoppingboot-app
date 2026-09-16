
class ShoppingCart {
  constructor(storageKey = 'bootstrap_cart_demo') {
    this.storageKey = storageKey;
    this.items = this.loadFromLocalStorage();
  }

  loadFromLocalStorage() {
    const data = localStorage.getItem(this.storageKey);
    return data ? JSON.parse(data) : [];
  }

  saveToLocalStorage() {
    localStorage.setItem(this.storageKey, JSON.stringify(this.items));
    window.dispatchEvent(new Event('cartUpdated'));
  }


  addItem(product, size, quantity = 1) {
    const existingItem = this.items.find(
      item => item.id === product.id && item.size === size
    );

    if (existingItem) {
      existingItem.quantity += quantity;
    } else {
      this.items.push({
        id: product.id,
        name: product.name,
        price: product.price,
        color: product.color,
        size: size,
        quantity: quantity
      });
    }
    this.saveToLocalStorage();
  }

  updateQuantity(index, change) {
    if (this.items[index]) {
      this.items[index].quantity += change;
      if (this.items[index].quantity <= 0) {
        this.items.splice(index, 1);
      }
      this.saveToLocalStorage();
    }
  }

  removeItem(index) {
    if (this.items[index]) {
      this.items.splice(index, 1);
      this.saveToLocalStorage();
    }
  }

  getItems() {
    return this.items;
  }

  getTotalCount() {
    return this.items.reduce((sum, item) => sum + item.quantity, 0);
  }

  getTotalPrice() {
    return this.items.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  }

  clearCart() {
        this.items = [];
        localStorage.removeItem(this.storageKey);
        window.dispatchEvent(new Event('cartUpdated'));
      }
}


const cart = new ShoppingCart();