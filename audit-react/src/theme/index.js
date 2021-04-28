import {createMuiTheme} from "@material-ui/core";
import {MuiCardStyle, MuiCardHeaderStyle, MuiCardActionsStyle} from "./overrides/CardStyle";
import {MuiTableStyle, MuiTableHeadStyle, MuiTableCellStyle} from "./overrides/TableStyle";
import {MuiListItemStyle} from "./overrides/ListStyle";
import {typographyStyle} from "./typography/TypographyStyle";
import {MuiPaperStyle} from "./overrides/PaperStyle";
import {PaletteStyle} from "./palette/PaletteStyle";
import {MuiDrawerStyle} from "./overrides/DrawerStyle";
import {MuiBreadcrumbsStyle} from "./overrides/BreadcrumbsStyle";
import {MuiSvgIconStyle} from "./overrides/SvgIconStyle";

const theme = createMuiTheme({

    palette: PaletteStyle,

    typography: typographyStyle,

    overrides: {

        MuiDrawer: MuiDrawerStyle,

        MuiPaper: MuiPaperStyle,

        MuiCard: MuiCardStyle,

        MuiCardHeader: MuiCardHeaderStyle,

        MuiCardActions: MuiCardActionsStyle,

        MuiTable: MuiTableStyle,

        MuiTableHead: MuiTableHeadStyle,

        MuiTableCell: MuiTableCellStyle,

        MuiListItem: MuiListItemStyle,

        MuiBreadcrumbs: MuiBreadcrumbsStyle,

        MuiSvgIcon: MuiSvgIconStyle,
    },
});

export default theme;