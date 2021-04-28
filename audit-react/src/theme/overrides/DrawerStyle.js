export const MuiDrawerStyle = {

    docked: {
        flex: '0 0 auto',
        width: 250,

        transition: 'width .2s ease-in-out 200ms',
        '-moz-transition': 'width .2s ease-in-out 200ms', /* Firefox 4 */
        '-webkit-transition': 'width .2s ease-in-out 200ms', /* Safari 和 Chrome */
        '-o-transition': 'width .2s ease-in-out 200ms', /* Opera */
    },

    paper: {
        width: 250,
        top: 0,
        flex: '1 0 auto',
        height: '100%',
        display: 'flex',
        outline: 0,
        zIndex: 1200,
        position: 'fixed',
        overflowY: 'auto',
        flexDirection: 'column',
        paddingTop: '1rem',
        paddingBottom: '1rem',
        '-webkit-overflow-scrolling': 'touch',

        transition: 'width .2s ease-in-out 200ms',
        '-moz-transition': 'width .2s ease-in-out 200ms', /* Firefox 4 */
        '-webkit-transition': 'width .2s ease-in-out 200ms', /* Safari 和 Chrome */
        '-o-transition': 'width .2s ease-in-out 200ms', /* Opera */
    }
}