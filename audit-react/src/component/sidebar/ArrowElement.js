import React from "react";
import {ChevronRight, KeyboardArrowDown} from "@material-ui/icons";
import propTypes from "prop-types";
import {makeStyles} from "@material-ui/styles";

const useStyles = makeStyles((theme) => ({
    navListItemFatherArrow: {
        marginLeft: 'auto',
        width: '1rem !important',
        height: '1rem !important',
        transition: 'all .15s ease',
    },
}));

export default function ArrowElement(props) {

    const {navListItemFatherArrow} = useStyles();

    const {isExpand} = props;

    return (
        <React.Fragment>
            {
                isExpand ?
                    <KeyboardArrowDown className={navListItemFatherArrow}/>
                    :<ChevronRight className={navListItemFatherArrow}/>
            }
        </React.Fragment>
    );
}

ArrowElement.propTypes = {
    isExpand: propTypes.bool
}