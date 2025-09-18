import React from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Drawer, List, ListItem, ListItemText, Box, Container } from '@mui/material';
import EntityList from './components/EntityList';
import EntityForm from './components/EntityForm';
import LogsManager from './components/LogsManager';

const entities = [
  {
    name: 'Departments',
    path: 'departments',
    api: '/api/departments',
    fields: [
      { key: 'name', label: 'Name' },
      { key: 'description', label: 'Description' },
    ],
    formFields: [
      { key: 'name', label: 'Name', type: 'text', required: true },
      { key: 'description', label: 'Description', type: 'text' },
    ],
  },
  {
    name: 'Employees',
    path: 'employees',
    api: '/api/employees',
    fields: [
      { key: 'firstName', label: 'First Name' },
      { key: 'lastName', label: 'Last Name' },
      { key: 'email', label: 'Email' },
      { key: 'hireDate', label: 'Hire Date' },
      { key: 'salary', label: 'Salary' },
      { key: 'departmentName', label: 'Department' },
      { key: 'positionName', label: 'Position' },
      { key: 'isActive', label: 'Active', type: 'boolean' },
    ],
    formFields: [
      { key: 'firstName', label: 'First Name', type: 'text', required: true },
      { key: 'lastName', label: 'Last Name', type: 'text', required: true },
      { key: 'email', label: 'Email', type: 'email', required: true },
      { key: 'hireDate', label: 'Hire Date', type: 'date', required: true },
      { key: 'salary', label: 'Salary', type: 'number', required: true },
      { key: 'departmentName', label: 'Department Name', type: 'text', required: true },
      { key: 'positionName', label: 'Position Name', type: 'text', required: true },
      { key: 'username', label: 'Username', type: 'text', required: true },
    ],
  },
  {
    name: 'Positions',
    path: 'positions',
    api: '/api/positions',
    fields: [
      { key: 'name', label: 'Name' },
      { key: 'description', label: 'Description' },
      { key: 'minSalary', label: 'Min Salary' },
      { key: 'maxSalary', label: 'Max Salary' },
    ],
    formFields: [
      { key: 'name', label: 'Name', type: 'text', required: true },
      { key: 'description', label: 'Description', type: 'text' },
      { key: 'minSalary', label: 'Min Salary', type: 'number' },
      { key: 'maxSalary', label: 'Max Salary', type: 'number' },
    ],
  },
  {
    name: 'Roles',
    path: 'roles',
    api: '/api/roles',
    fields: [
      { key: 'name', label: 'Name' },
    ],
    formFields: [
      { key: 'name', label: 'Name', type: 'text', required: true },
    ],
  },
  {
    path: 'users',
    name: 'Users',
    api: '/api/users',
    fields: [
      { key: 'username', label: 'Username' },
      { key: 'roles', label: 'Roles' } // For display in EntityList
    ],
    formFields: [
      { key: 'username', label: 'Username', type: 'text', required: true },
      { key: 'password', label: 'Password', type: 'password', required: false }, // Password not required for edit
      { key: 'roleIds', label: 'Roles', type: 'select-multiple', required: true } // For multi-select in form
    ]
  }
];

function App() {
  return (
    <Box sx={{ display: 'flex', bgcolor: 'background.default', minHeight: '100vh' }}>
      <AppBar position="fixed" sx={{ bgcolor: '#1E1E1E' }}>
        <Toolbar>
          <Typography variant="h6" sx={{ fontWeight: 500 }}>
            Employee Management
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        sx={{
          width: 240,
          flexShrink: 0,
          [`& .MuiDrawer-paper`]: {
            width: 240,
            boxSizing: 'border-box',
            marginTop: '64px',
            bgcolor: '#1E1E1E',
            borderRight: '1px solid #2C2C2C',
          },
        }}
      >
        <List>
          {entities.map((entity) => (
            <ListItem
              key={entity.path}
              component={Link}
              to={`/${entity.path}`}
              sx={{
                '&:hover': {
                  bgcolor: '#2C2C2C',
                },
              }}
            >
              <ListItemText
                primary={entity.name}
                primaryTypographyProps={{ color: 'text.primary' }}
              />
            </ListItem>
          ))}
          <ListItem
            key="logs"
            component={Link}
            to="/logs"
            sx={{
              '&:hover': {
                bgcolor: '#2C2C2C',
              },
            }}
          >
            <ListItemText
              primary="Logs"
              primaryTypographyProps={{ color: 'text.primary' }}
            />
          </ListItem>
        </List>
      </Drawer>
      <Box component="main" sx={{ flexGrow: 1, p: 3, marginTop: '64px' }}>
        <Container maxWidth="lg">
          <Routes>
            {entities.map((entity) => (
              <React.Fragment key={entity.path}>
                <Route path={`/${entity.path}`} element={<EntityList entity={entity} />} />
                <Route path={`/${entity.path}/add`} element={<EntityForm entity={entity} />} />
                <Route path={`/${entity.path}/edit/:id`} element={<EntityForm entity={entity} />} />
              </React.Fragment>
            ))}
            <Route path="/logs" element={<LogsManager />} />
            <Route
              path="/"
              element={<Typography variant="h4" sx={{ mt: 4 }}>Welcome to Employee Management</Typography>}
            />
          </Routes>
        </Container>
      </Box>
    </Box>
  );
}

export default App;