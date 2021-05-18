import React from 'react';
import { ThemeProvider } from '@material-ui/core';
import theme from '../theme';
import routes from '../routes';

export default function App() {
  return <ThemeProvider theme={theme}>{routes}</ThemeProvider>;
}
