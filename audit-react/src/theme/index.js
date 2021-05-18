import { createMuiTheme } from '@material-ui/core';
import {
  MuiCardStyle,
  MuiCardHeaderStyle,
  MuiCardActionsStyle
} from './overrides/CardStyle';
import {
  MuiTableStyle,
  MuiTableHeadStyle,
  MuiTableCellStyle
} from './overrides/TableStyle';
import { MuiListItemStyle } from './overrides/ListStyle';
import { typographyStyle } from './typography/TypographyStyle';
import { MuiPaperStyle } from './overrides/PaperStyle';
import { PaletteStyle } from './palette/PaletteStyle';
import { MuiDrawerStyle } from './overrides/DrawerStyle';
import { MuiBreadcrumbsStyle } from './overrides/BreadcrumbsStyle';
import { MuiSvgIconStyle } from './overrides/SvgIconStyle';

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

    MuiOutlinedInput: {
      root: {
        padding: 0
      },
      input: {
        border: 0,
        padding: '.625rem .75rem'
      },
      multiline: {
        padding: '.625rem .75rem'
      }
    },

    MuiInputBase: {
      root: {
        fontFamily: 'OpenSans, MSYaHei',
        width: '100%'
      },
      input: {
        //border: '1px solid #cad1d7',
        height: 'calc(1.5em + 1.25rem + 2px)',
        margin: 0,
        padding: '.625rem .75rem',
        fontSize: '.875rem',
        minWidth: 0,
        background: 'none',
        boxShadow: 'none',
        boxSizing: 'border-box',
        transition: 'all .2s cubic-bezier(.68,-.55,.265,1.55)',
        fontWeight: 400,
        lineHeight: 1.5,
        borderRadius: '.175rem',
        animationName: 'mui-auto-fill-cancel',
        letterSpacing: 'inherit',
        backgroundClip: 'padding-box',
        //backgroundColor: '#FFFFFF',
        animationDuration: '10ms',
        '-webkit-tap-highlight-color': 'transparent'
      }
    }
  }
});

export default theme;
