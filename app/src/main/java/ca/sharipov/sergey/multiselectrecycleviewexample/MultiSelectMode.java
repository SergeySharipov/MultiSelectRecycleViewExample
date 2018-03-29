package ca.sharipov.sergey.multiselectrecycleviewexample;

import java.util.Set;

public interface MultiSelectMode<T> {

    Set<T> getSelectedItemsId();

    void setSelectedItemsId(Set<T> mSelectedItemsId);

    void addSelectedItemId(T selectedItemId);

    void removeSelectedItemId(T selectedItemId);

    void removeAllSelected();

    int getSelectedItemsCount();

    void selectAll();

    boolean isAllSelected();

    boolean isSomethingSelected();
}
