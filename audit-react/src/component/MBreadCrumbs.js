import {Breadcrumbs, Link, SvgIcon} from "@material-ui/core";
import {string} from 'prop-types';
import {makeStyles} from "@material-ui/styles";


const useStyles = makeStyles((theme) => ({
    homeIcon: {
        position: 'relative',
        width: '1.25rem !important',
        height: '1.25rem !important',
        fontSize: '1.7142857142857142rem',
        marginTop: 3
    },
}));

export default function MBreadCrumbs(props) {

    const classes = useStyles();
    const {path} = props;

    let fullPath = path;

    if(path.charAt(0) !== "/") {
        fullPath = "/" + path;
    }

    const sep = fullPath.split("/");

    const list = sep.map((item, index) => {
        let tmp = item.trim();
        if(tmp === "") return null;
        return (
            <Link key={index} color="inherit" href={fullPath}>
                {tmp}
            </Link>
        )
    });

    return (
        <Breadcrumbs aria-label="breadcrumb" separator="-"
                     style={{background: 'transparent', marginBottom: 0}}>
            <Link color="inherit" href="/">
                <SvgIcon className={classes.homeIcon}>
                    <path d="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z"/>
                </SvgIcon>
            </Link>
            {list}
        </Breadcrumbs>
    );
}


MBreadCrumbs.propTypes = {
    path: string
}