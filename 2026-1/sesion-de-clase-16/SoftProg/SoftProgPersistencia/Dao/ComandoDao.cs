using System.Data.Common;

namespace SoftProgPersistencia.Dao;

public delegate T ComandoDao<out T>(DbConnection connection);
