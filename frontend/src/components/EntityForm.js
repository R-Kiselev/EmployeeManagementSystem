import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { TextField, Button, Box, Typography, Checkbox, FormControlLabel, Paper } from '@mui/material';
import api from '../api';

function EntityForm({ entity }) {
  const { id } = useParams();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({});
  const [error, setError] = useState(null);

  useEffect(() => {
    if (id) {
      fetchItem();
    } else {
      setFormData(
        entity.formFields.reduce(
          (acc, field) => ({
            ...acc,
            [field.key]: field.type === 'checkbox' ? false : (field.key === 'hireDate' && entity.path === 'employees' ? new Date().toISOString().split('T')[0] : ''),
          }),
          {}
        )
      );
    }
  }, [id, entity.api, entity.formFields, entity.path]);

  const fetchItem = async () => {
    try {
      if (!id || isNaN(id)) {
        throw new Error('Invalid employee ID');
      }
      const response = await api.get(`${entity.api}/${id}`);
      const data = response.data;

      if (entity.path === 'employees') {
        setFormData({
          firstName: data.firstName || '',
          lastName: data.lastName || '',
          email: data.email || '',
          hireDate: data.hireDate || '',
          salary: data.salary || '',
          departmentName: data.department?.name || '',
          positionName: data.position?.name || '',
          username: data.user?.username || '',
        });
      } else if (entity.path === 'users') {
        const roleNames = data.roleIds
          ? (await Promise.all(
              data.roleIds.map((roleId) => api.get(`/api/roles/${roleId}`))
            )).map((res) => res.data.name).join(', ')
          : '';
        setFormData({
          username: data.username || '',
          password: '',
          roleNames: roleNames,
        });
      } else {
        const filteredData = Object.fromEntries(
          Object.entries(data).filter(([key]) => key !== 'id')
        );
        setFormData(filteredData);
      }
    } catch (error) {
      console.error(`Error fetching ${entity.name}:`, error);
      setError(error.message || 'Failed to load data.');
    }
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleHireDateFocus = () => {
    if (entity.path === 'employees' && !formData.hireDate) {
      setFormData((prev) => ({
        ...prev,
        hireDate: new Date().toISOString().split('T')[0],
      }));
    }
  };

  const resolveNamesToIds = async (data) => {
    if (entity.path === 'employees') {
      try {
        const departmentRes = await api.get('/api/departments');
        const department = departmentRes.data.find((d) => d.name === data.departmentName);
        if (!department) throw new Error(`Department "${data.departmentName}" not found`);

        const positionRes = await api.get('/api/positions');
        const position = positionRes.data.find((p) => p.name === data.positionName);
        if (!position) throw new Error(`Position "${data.positionName}" not found`);

        let userRes;
        try {
          userRes = await api.get(`/api/users/username/${data.username}`);
        } catch (err) {
          if (err.response && err.response.status === 404) {
            throw new Error(`User with username "${data.username}" not found`);
          } else {
            throw err;
          }
        }
        if (!userRes.data) throw new Error(`User with username "${data.username}" not found`);

        return {
          firstName: data.firstName,
          lastName: data.lastName,
          email: data.email,
          hireDate: data.hireDate,
          salary: data.salary,
          isActive: true,
          departmentId: department.id,
          positionId: position.id,
          userId: userRes.data.id,
        };
      } catch (error) {
        throw error;
      }
    } else if (entity.path === 'users') {
      try {
        const roleNames = data.roleNames ? data.roleNames.split(',').map((name) => name.trim()).filter(Boolean) : [];
        const rolesRes = await api.get('/api/roles');
        const roleIds = roleNames.map((name) => {
          const role = rolesRes.data.find((r) => r.name === name);
          if (!role) throw new Error(`Role "${name}" not found`);
          return role.id;
        });
        return {
          username: data.username,
          password: data.password,
          roleIds,
        };
      } catch (error) {
        throw error;
      }
    }
    return data;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setError(null);
      const resolvedData = await resolveNamesToIds(formData);
      if (id) {
        await api.put(`${entity.api}/${id}`, resolvedData);
      } else {
        await api.post(entity.api, resolvedData);
      }
      navigate(`/${entity.path}`);
    } catch (error) {
      console.error(`Error saving ${entity.name}:`, error);
      if (error.message && !error.message.includes('Request failed with status code')) {
        setError(error.message);
      } else if (error.response && error.response.data && error.response.data.message) {
        setError(error.response.data.message);
      } else {
        setError(`Error saving ${entity.name.slice(0, -1)}.`);
      }
    }
  };

  return (
    <Box sx={{ maxWidth: 600, mx: 'auto', mt: 4 }}>
      <Paper elevation={3} sx={{ p: 3, bgcolor: '#1E1E1E' }}>
        <Typography variant="h4" sx={{ mb: 3, fontWeight: 500 }}>
          {id ? `Edit ${entity.name.slice(0, -1)}` : `Add ${entity.name.slice(0, -1)}`}
        </Typography>
        {error && (
          <Typography color="error" sx={{ mb: 2 }}>
            {error}
          </Typography>
        )}
        <form onSubmit={handleSubmit}>
          {entity.formFields.map((field) => (
            <Box key={field.key} sx={{ mb: 2 }}>
              {field.type === 'checkbox' ? (
                <FormControlLabel
                  control={
                    <Checkbox
                      name={field.key}
                      checked={formData[field.key] || false}
                      onChange={handleChange}
                      sx={{
                        color: '#00C4B4',
                        '&.Mui-checked': {
                          color: '#00C4B4',
                        },
                      }}
                    />
                  }
                  label={field.label}
                  sx={{ color: 'text.primary' }}
                />
              ) : (
                <TextField
                  fullWidth
                  label={field.label}
                  name={field.key}
                  type={field.type || 'text'}
                  value={formData[field.key] || ''}
                  onChange={handleChange}
                  required={field.required}
                  variant="outlined"
                  InputLabelProps={{ style: { color: '#B0B0B0' } }}
                  InputProps={{
                    style: { color: '#FFFFFF' },
                    ...(field.type === 'date' && {
                      inputProps: {
                        style: { color: formData[field.key] ? '#FFFFFF' : '#B0B0B0' },
                        ...(field.key === 'hireDate' && entity.path === 'employees' && {
                          onFocus: handleHireDateFocus,
                        }),
                      },
                    }),
                  }}
                />
              )}
            </Box>
          ))}
          <Box sx={{ display: 'flex', gap: 2 }}>
            <Button type="submit" variant="contained" color="primary">
              Save
            </Button>
            <Button
              variant="outlined"
              onClick={() => navigate(`/${entity.path}`)}
              sx={{
                borderColor: '#00C4B4',
                color: '#00C4B4',
                '&:hover': {
                  borderColor: '#00A89A',
                  bgcolor: '#2C2C2C',
                },
              }}
            >
              Cancel
            </Button>
          </Box>
        </form>
      </Paper>
    </Box>
  );
}

export default EntityForm;