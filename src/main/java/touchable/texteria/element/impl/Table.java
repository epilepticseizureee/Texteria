package touchable.texteria.element.impl;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import touchable.texteria.element.Element2D;
import touchable.texteria.util.ByteMap;
import touchable.texteria.misc.IWritable;

import java.util.List;

@SuppressWarnings("all")
public class Table extends Element2D<Table> {
    private final List<Column> columns = Lists.newArrayListWithCapacity(4);
    private final List<String[]> rows = Lists.newArrayListWithCapacity(8);
    private String title = null;
    private int titleColor = -769226;
    private int headingColor = -5317;
    private boolean drawBack = false;
    private int scrollbarColor = -5317;
    private int rowHoverColor = 1140946643;
    private int maxRows = 0;

    public Table(String id) {
        super(id);
    }

    public Table setTitle(String title) {
        this.title = title;
        return this;
    }

    public Table setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public Table setHeadingColor(int headingColor) {
        this.headingColor = headingColor;
        return this;
    }

    public Table setDrawBack(boolean drawBack) {
        this.drawBack = drawBack;
        return this;
    }

    public Table setScrollbarColor(int scrollbarColor) {
        this.scrollbarColor = scrollbarColor;
        return this;
    }

    public Table setRowHoverColor(int rowHoverColor) {
        this.rowHoverColor = rowHoverColor;
        return this;
    }

    public Table setMaxRows(int maxRows) {
        this.maxRows = maxRows;
        return this;
    }

    public Table addColumn(Column column) {
        columns.add(column);
        return this;
    }

    public Table addRow(String[] strings) {
        rows.add(strings);
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);

        if (title != null) {
            map.put("title", title);
        }
        if (titleColor != -769226) {
            map.put("title.c", titleColor);
        }
        if (headingColor != -5317) {
            map.put("heading.c", headingColor);
        }
        if (drawBack) {
            map.put("drawBack", true);
        }
        if (scrollbarColor != -5317) {
            map.put("sb.c", scrollbarColor);
        }
        if (rowHoverColor != 1140946643) {
            map.put("rh.c", rowHoverColor);
        }

        ByteMap[] columnsMaps = new ByteMap[columns.size()];
        int i = 0;

        for (Column column : this.columns) {
            columnsMaps[i++] = column.write(new ByteMap());
        }
        map.put("cols", columnsMaps);
        map.put("rows", rows.toArray(new String[0][]));

        if (maxRows != 0) map.put("maxRows", maxRows);

        return map;
    }

    @Override
    public String getElementType() {
        return "Table";
    }

    public static class Column implements IWritable {
        private final String name;
        private int width = -1;
        private boolean center = false;
        private int color = -1;

        public Column(String name, int width) {
            this.name = ChatColor.translateAlternateColorCodes('&', name);
            this.width = width;
        }

        public Column setCenter(boolean center) {
            this.center = center;
            return this;
        }

        public Column setColor(int color) {
            this.color = color;
            return this;
        }

        @Override
        public ByteMap write(ByteMap map) {
            if (name != null) {
                map.put("n", name);
            }
            if (width != -1) {
                map.put("w", width);
            }
            if (center) {
                map.put("c", true);
            }
            if (color != -1) {
                map.put("t", color);
            }

            return map;
        }
    }
}

