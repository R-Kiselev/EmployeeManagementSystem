import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#00C4B4', // Tiffany Blue
      contrastText: '#FFFFFF',
    },
    secondary: {
      main: '#B0B0B0',
      contrastText: '#FFFFFF',
    },
    background: {
      default: '#121212',
      paper: '#1E1E1E',
    },
    text: {
      primary: '#FFFFFF',
      secondary: '#B0B0B0',
    },
  },
  typography: {
    fontFamily: 'Roboto, sans-serif',
    h4: {
      fontWeight: 500,
    },
    button: {
      textTransform: 'none',
    },
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 8,
          padding: '8px 16px',
          '&:hover': {
            backgroundColor: '#00A89A', // Slightly darker Tiffany for hover
          },
        },
      },
    },
    MuiTable: {
      styleOverrides: {
        root: {
          backgroundColor: '#1E1E1E',
        },
      },
    },
    MuiTableCell: {
      styleOverrides: {
        root: {
          borderBottom: '1px solid #2C2C2C',
        },
        head: {
          color: '#B0B0B0',
          fontWeight: 500,
        },
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          '& .MuiOutlinedInput-root': {
            '& fieldset': {
              borderColor: '#2C2C2C',
            },
            '&:hover fieldset': {
              borderColor: '#00C4B4',
            },
            '&.Mui-focused fieldset': {
              borderColor: '#00C4B4',
            },
          },
        },
      },
    },
  },
});

export default theme;