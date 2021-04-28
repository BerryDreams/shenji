import React from "react";
import DashboardLayout from "./layout/DashboardLayout";
import {ThemeProvider} from "@material-ui/core";
import theme from "../theme";

export default function App() {

    return (
        <ThemeProvider theme={theme}>
            <DashboardLayout/>
        </ThemeProvider>
    );
}