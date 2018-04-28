package nl.jketelaar.project.sync.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TableColumn;

public class PercentageTableColumn extends TableColumn {

    private DoubleProperty percentageWidth = new SimpleDoubleProperty();

    public PercentageTableColumn() {
        // The table property is set delayed each column by column
        tableViewProperty().addListener((observable, oldValue, newValue) -> {
            ReadOnlyDoubleProperty tableWidth = getTableView().widthProperty();
            this.prefWidthProperty().bind(createPercentageWidthBinding(tableWidth));
        });
    }

    private DoubleBinding createPercentageWidthBinding(ReadOnlyDoubleProperty tableWidth) {
        return Bindings.createDoubleBinding(
                () -> {
                    // If the user doesn't define the percentage
                    if (percentageWidth.get() <= 0) {
                        return getWidth();
                    } else {
                        double tableWidthDouble = tableWidth.get();
                        return percentageWidth.get() * tableWidthDouble / 100;
                    }
                }, percentageWidth, tableWidth);
    }

    public double getPercentageWidth() {
        return percentageWidth.get();
    }

    public DoubleProperty percentageWidthProperty() {
        return percentageWidth;
    }

    public void setPercentageWidth(double percentageWidth) {
        this.percentageWidth.set(percentageWidth);
    }
}