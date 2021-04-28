export const MuiBreadcrumbsStyle = {
    root: {

    },

    ol: {
        margin: 0,
        display: 'flex',
        padding: '.5rem 1rem',
        flexWrap: 'wrap',
        listStyle: 'none',
        alignItems: 'center',
        borderRadius: '.375rem',
        backgroundColor: 'transparent',
    },

    li: {
        fontSize: '.875rem',
        '& a': {
            color: '#f6f9fc',
            fontWeight: 500,
            textDecoration: 'none',
        }
    },

    separator: {
        color: '#FFFFFF',
        display: 'flex',
        marginLeft: 8,
        userSelect: 'none',
        marginRight: 8,
        paddingRight: '.5rem',
    }
}