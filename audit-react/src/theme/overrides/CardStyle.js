export const MuiCardStyle = {
    root: {
        border: 0,
            display: 'flex',
            overflow: 'unset',
            position: 'relative',
            minWidth: 0,
            wordWrap: 'breakWord',
            marginBottom: 30,
            flexDirection: 'column',
            backgroundClip: 'initial',
            backgroundColor: '#FFFFFF',
    }
};

export const MuiCardHeaderStyle = {
    root: {
        display: 'flex',
            padding: '1.25rem 1.5rem',
            alignItems: 'center',
            borderBottom: '1px solid rgba(0, 0, 0,.05)',
            marginBottom: 0,
            borderRadius: 'calc(.375rem - 1px) calc(.375rem - 1px) 0 0',
            backgroundColor: '#FFFFFF',
    },
    content: {
        flex: '1 1 auto'
    },
    title: {
        color: '#32325d',
            fontSize: '1.0625rem',
            fontFamily: 'inherit',
            fontWeight: 600,
            lineHeight: 1.5,
            marginBottom: '0px !important'
    }
};

export const MuiCardActionsStyle = {
    root: {
        '&:last-child': {
            padding: '1.25rem 1.5rem',
                borderTop: '1px solid rgba(0, 0, 0,.05)',
                borderRadius: '0 0 calc(.375rem - 1px) calc(.375rem - 1px)',
                backgroundColor: '#FFFFFF',
        }
    }
};