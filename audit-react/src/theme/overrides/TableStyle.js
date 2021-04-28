export const MuiTableStyle = {
    root: {
        color: '#525f7f',
            width: '100%',
            display: 'table',
            borderSpacing: 0,
            borderCollapse: 'collapse',
            backgroundColor: 'transparent',
    }
};

export const MuiTableHeadStyle = {
    root: {
        display: 'table-header-group',
            color: '#8898aa',
            backgroundColor: '#f6f9fc',
            padding: '4px 0',
            "& .MuiTableCell-head": {
            color: '#8898aa',
                backgroundColor: '#f6f9fc',
        }
    }
};

export const MuiTableCellStyle = {
    root: {
        borderTop: 0,
            padding: '1rem 1.5rem',
            verticalAlign: 'middle',
            display: 'table-cell',
            fontSize: '.8125rem',
            textAlign: 'left',
            fontWeight: '400',
            lineHeight: '1.43',
            whiteSpace: 'nowrap',
            borderBottom: '1px solid #e9ecef',
    },
    head: {
        fontSize: '.65rem',
            borderTop: '1px solid #e9ecef',
            fontWeight: 600,
            lineHeight: '1.7142857142857142rem',
            paddingTop: '.75rem',
            whiteSpace: 'nowrap',
            borderBottom: '1px solid #e9ecef',
            letterSpacing: '1px',
            paddingBottom: '.75rem',
            textTransform: 'uppercase',
            verticalAlign: 'bottom',
    }
};