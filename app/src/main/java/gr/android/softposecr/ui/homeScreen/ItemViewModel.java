package gr.android.softposecr.ui.homeScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;
import gr.android.softposecr.R;
import gr.android.softposecr.domain.models.Item;

@HiltViewModel
public class ItemViewModel extends ViewModel {
    private final MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Item>> cartItemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Item>> filteredItemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Float> cartTotalLiveData = new MutableLiveData<>(0.0f);
    private final Map<String, Integer> itemQuantities = new HashMap<>();

    @Inject
    public ItemViewModel() {
        loadItems();
    }

    public LiveData<List<Item>> getItems() {
        return itemsLiveData;
    }

    public LiveData<List<Item>> getCartItems() {
        return cartItemsLiveData;
    }

    public LiveData<List<Item>> getFilteredItems() {
        return filteredItemsLiveData;
    }

    public LiveData<Float> getCartTotal() {
        return cartTotalLiveData;
    }

    public Map<String, Integer> getItemQuantities() {
        return itemQuantities;
    }

    public void incrementItemQuantity(String itemTitle) {
        itemQuantities.put(itemTitle, itemQuantities.getOrDefault(itemTitle, 0) + 1);
        updateCartItems();
        updateCartTotal();
    }

    public void decrementItemQuantity(String itemTitle) {
        int currentQuantity = itemQuantities.getOrDefault(itemTitle, 0);
        if (currentQuantity > 0) {
            itemQuantities.put(itemTitle, currentQuantity - 1);
            updateCartItems();
            updateCartTotal();
        }
    }

    public void filterItems(String query) {
        List<Item> allItems = itemsLiveData.getValue();
        if (allItems == null) return;

        if (query == null || query.trim().isEmpty()) {
            filteredItemsLiveData.setValue(allItems);
            return;
        }

        String lowerCaseQuery = query.toLowerCase().trim();
        List<Item> filtered = allItems.stream()
            .filter(item ->
                item.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                item.getOverview().toLowerCase().contains(lowerCaseQuery))
            .collect(Collectors.toList());

        filteredItemsLiveData.setValue(filtered);
    }

    private void loadItems() {
        List<Item> items = new ArrayList<>();
        // Add sample items - in a real app, this would come from a repository
        items.add(new Item("Classic Burger", 7.50f, "Classic beef patty with lettuce, tomato, and cheese", R.drawable.burger_classic));
        items.add(new Item("Premium Burger", 8.99f, "Premium beef patty with special toppings, double cheese and gourmet sauce", R.drawable.burger_premium));
        items.add(new Item("Double Cheeseburger", 11.99f, "Two beef patties with double cheese and special sauce", R.drawable.burger_double));
        items.add(new Item("Chicken Nuggets", 8.99f, "10 piece chicken nuggets", R.drawable.chicken_nuggets));
        items.add(new Item("Fries", 3.99f, "French Fried Potatoes", R.drawable.fries));
//        items.add(new Item("Mug of Beer", 2.99f, "Barrel Beer - 500ml", R.drawable.beer_mug));
        items.add(new Item("Sauce", 0.99f, "Zappas Special Sauce", R.drawable.sauce));
        items.add(new Item("Mug of Beer", 2.99f, "Barrel Beer - 500ml", R.drawable.beer));
        items.add(new Item("Water Bottle", 0.50f, "Zagori - 500ml", R.drawable.water_bottle_500ml));

        itemsLiveData.setValue(items);
        filteredItemsLiveData.setValue(items); // Initialize filtered items with all items
    }

    public void updateCartItemsAndTotal() {
        updateCartItems();
        updateCartTotal();
    }

    private void updateCartItems() {
        List<Item> allItems = itemsLiveData.getValue();
        if (allItems != null) {
            List<Item> cartItems = allItems.stream()
                .filter(item -> itemQuantities.getOrDefault(item.getTitle(), 0) > 0)
                .collect(Collectors.toList());
            cartItemsLiveData.setValue(cartItems);
        }
    }

    private void updateCartTotal() {
        float total = 0.0f;
        List<Item> items = itemsLiveData.getValue();
        if (items != null) {
            for (Item item : items) {
                int quantity = itemQuantities.getOrDefault(item.getTitle(), 0);
                total += item.getPrice() * quantity;
            }
        }
        cartTotalLiveData.setValue(total);
    }

    public int getItemQuantity(Item item) {
        return itemQuantities.getOrDefault(item.getTitle(), 0);
    }
}
